package com.example.helloworldthegame;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FourthActivity extends AppCompatActivity {

    private ImageView redPlayer;
    private ImageView bluePlayer;
    private ImageView soccerBall;
    private View leftGoal;
    private View rightGoal;
    private ConstraintLayout field;
    private Handler handler = new Handler();

    private float ballSpeedX = 5; // Initial speed of the ball
    private float ballSpeedY = 2;  // Initial speed of the ball

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // Initialize ImageViews for players and ball
        redPlayer = findViewById(R.id.red_player);
        bluePlayer = findViewById(R.id.blue_player);
        soccerBall = findViewById(R.id.soccer_ball);
        field = findViewById(R.id.field);
        leftGoal = findViewById(R.id.leftGoal);
        rightGoal = findViewById(R.id.rightGoal);

        // Make the players draggable
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performClick(); // Ensure accessibility services handle the click event
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                    return true;
                }
                return false;
            }
        };

        redPlayer.setOnTouchListener(touchListener);
        bluePlayer.setOnTouchListener(touchListener);

        // Set up drag and drop listener for the field
        field.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();

                        // Get the coordinates where the player is dropped
                        float x = event.getX() - draggedView.getWidth() / 2;
                        float y = event.getY() - draggedView.getHeight() / 2;

                        // Update the position of the dragged view
                        draggedView.setX(x);
                        draggedView.setY(y);

                        // Ensure the player is visible after being dropped
                        draggedView.setVisibility(View.VISIBLE);

                        // Check if the ball touches the player
                        checkBallCollision();
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        View droppedView = (View) event.getLocalState();
                        if (!event.getResult()) {
                            droppedView.setVisibility(View.VISIBLE);
                        }
                        return true;
                    default:
                        return true;
                }
            }
        });

        // Start ball physics loop
        startBallPhysics();
    }

    private void startBallPhysics() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveBall();
                checkBallCollision();
                checkGoal();
                handler.postDelayed(this, 16); // 60 FPS
            }
        }, 16);
    }

    private void moveBall() {
        // Ensure minimum speed to prevent the ball from getting stuck
        if (Math.abs(ballSpeedX) < 1) {
            ballSpeedX = ballSpeedX < 0 ? -1 : 1;
        }
        if (Math.abs(ballSpeedY) < 1) {
            ballSpeedY = ballSpeedY < 0 ? -1 : 1;
        }

        // Move the ball
        soccerBall.setX(soccerBall.getX() + ballSpeedX);
        soccerBall.setY(soccerBall.getY() + ballSpeedY);

        // Ball bouncing off the top and bottom of the screen
        if (soccerBall.getY() <= 0 || soccerBall.getY() + soccerBall.getHeight() >= field.getHeight()) {
            ballSpeedY = -ballSpeedY;
        }
    }

    private void checkBallCollision() {
        if (isViewOverlapping(soccerBall, redPlayer)) {
            Log.d("Collision", "Ball collided with Red Player");
            ballSpeedX = Math.abs(ballSpeedX) + 1;
            ballSpeedY += (Math.random() * 4 - 2); // Add randomness to Y direction
        } else if (isViewOverlapping(soccerBall, bluePlayer)) {
            Log.d("Collision", "Ball collided with Blue Player");
            ballSpeedX = -Math.abs(ballSpeedX) - 1;
            ballSpeedY += (Math.random() * 4 - 2); // Add randomness to Y direction
        }
    }

    private void checkGoal() {
        if (isViewOverlapping(soccerBall, leftGoal) || isViewOverlapping(soccerBall, rightGoal)) {
            // Reset the game state
            resetGame();
        }
    }

    private void resetGame() {
        // Reset players and ball positions
        redPlayer.setX(leftGoal.getX());
        redPlayer.setY(field.getHeight() / 2f - redPlayer.getHeight() / 2f);

        bluePlayer.setX(rightGoal.getX());
        bluePlayer.setY(field.getHeight() / 2f - bluePlayer.getHeight() / 2f);

        soccerBall.setX(field.getWidth() / 2f - soccerBall.getWidth() / 2f);
        soccerBall.setY(field.getHeight() / 2f - soccerBall.getHeight() / 2f);

        ballSpeedX = 5;
        ballSpeedY = 2;
    }

    private boolean isViewOverlapping(View view1, View view2) {
        Rect rect1 = new Rect();
        Rect rect2 = new Rect();

        view1.getHitRect(rect1);
        view2.getHitRect(rect2);

        // Shrink the hitboxes slightly to avoid false positives
        rect1.inset(55, 55);  // Adjust these values as necessary
        rect2.inset(55, 55);

        return Rect.intersects(rect1, rect2);
    }
}
