package com.example.helloworldthegame;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // Initialize ImageViews for players
        final ImageView redPlayer = findViewById(R.id.red_player);
        final ImageView bluePlayer = findViewById(R.id.blue_player);

        // Initialize Boxes
        final View box03 = findViewById(R.id.box03);
        final View box27 = findViewById(R.id.box27);

        // Set initial positions
        setInitialPosition(redPlayer, box03);
        setInitialPosition(bluePlayer, box27);

        // Make the players draggable
        redPlayer.setOnTouchListener(new View.OnTouchListener() {
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
        });

        bluePlayer.setOnTouchListener(new View.OnTouchListener() {
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
        });

        // Set up drag and drop listener for the ConstraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.field);
        constraintLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();

                        // Find the box where the player is dropped
                        View dropTarget = findViewAt(event.getX(), event.getY(), constraintLayout);

                        if (dropTarget != null) {
                            // Update the position to the new box
                            setInitialPosition(draggedView, dropTarget);
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setInitialPosition(View view, View box) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        // Center the view in the box using constraints
        params.leftToLeft = box.getId();
        params.rightToRight = box.getId();
        params.topToTop = box.getId();
        params.bottomToBottom = box.getId();

        view.setLayoutParams(params);
    }

    private View findViewAt(float x, float y, ConstraintLayout constraintLayout) {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            View child = constraintLayout.getChildAt(i);
            if (child instanceof View) {
                int[] location = new int[2];
                child.getLocationOnScreen(location);
                int left = location[0];
                int top = location[1];
                int right = left + child.getWidth();
                int bottom = top + child.getHeight();

                if (x >= left && x <= right && y >= top && y <= bottom) {
                    return child;
                }
            }
        }
        return null;
    }
}
