package com.example.bekircinar.lpreviewex.animation;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.example.bekircinar.lpreviewex.R;
import com.example.bekircinar.lpreviewex.util.AnimationUtil;

/**
 * Created by bekir.cinar on 10/07/14.
 */
public class AnimationActivity extends Activity implements View.OnClickListener {

    private int mWidth;
    private int mCx;
    private int mCy;
    private boolean hided = true;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //request for activity transition must be added before setContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //enter transaction setted
        getWindow().setEnterTransition(new Slide());

        setContentView(R.layout.activity_animaton);

        mView = findViewById(R.id.my_imageview);
        setVariables();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_button:
                if (hided) {
                    hided = false;
                    AnimationUtil.showItemWithAnim(mView, mCx, mCy, mWidth);
                } else {
                    hided = true;
                    AnimationUtil.hideItemWithAnim(mView);
                }
                break;
        }
    }

    private void setVariables() {
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = mView.getMeasuredWidth();
                mCx = (mView.getLeft() + mView.getRight()) / 2;
                mCy = (mView.getTop() + mView.getBottom()) / 2;
                if (Build.VERSION.SDK_INT < 16) {
                    mView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                mView.setVisibility(View.GONE);
            }
        });
    }
}
