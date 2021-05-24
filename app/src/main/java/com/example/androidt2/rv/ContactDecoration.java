package com.example.androidt2.rv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactDecoration extends RecyclerView.ItemDecoration {

    Drawable background;
    boolean initiated;

    private void init() {
        background = new ColorDrawable(Color.RED);
        initiated = true;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if (!initiated) {
            init();
        }

        // only if animation is in progress
        if (parent.getItemAnimator().isRunning()) {

            View lastViewComingDown = null;
            View firstViewComingUp = null;

            // this is fixed
            int left = 0;
            int right = parent.getWidth();

            // this we need to find out
            int top = 0;
            int bottom = 0;

            // find relevant translating views
            int childCount = parent.getLayoutManager().getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getLayoutManager().getChildAt(i);
                if (child.getTranslationY() < 0) {
                    // view is coming down
                    lastViewComingDown = child;
                } else if (child.getTranslationY() > 0) {
                    // view is coming up
                    if (firstViewComingUp == null) {
                        firstViewComingUp = child;
                    }
                }
            }

            if (lastViewComingDown != null && firstViewComingUp != null) {
                // views are coming down AND going up to fill the void
                top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
            } else if (lastViewComingDown != null) {
                // views are going down to fill the void
                top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                bottom = lastViewComingDown.getBottom();
            } else if (firstViewComingUp != null) {
                // views are coming up to fill the void
                top = firstViewComingUp.getTop();
                bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
            }

            background.setBounds(left, top, right, bottom);
            background.draw(c);

        }
        super.onDraw(c, parent, state);
    }
}
