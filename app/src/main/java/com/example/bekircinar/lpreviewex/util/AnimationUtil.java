package com.example.bekircinar.lpreviewex.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by bekir.cinar on 10/07/14.
 */
public class AnimationUtil {

    public static void showItemWithAnim(final View mView, int cx, int cy, int mWidth) {

        if (Build.VERSION.SDK_INT >= 20) {
            ValueAnimator anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, 0, mWidth);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mView.setVisibility(View.VISIBLE);
                }
            });

            anim.start();
        } else {
            mView.setVisibility(View.VISIBLE);
        }
    }

    public static void hideItemWithAnim(final View myView) {

        // get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int initialRadius = myView.getWidth();

        if (Build.VERSION.SDK_INT >= 20) {
            // create the animation (the final radius is zero)
            ValueAnimator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            myView.setVisibility(View.INVISIBLE);
        }

    }

}
