package com.example.bekircinar.lpreviewex.cardview;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bekircinar.lpreviewex.R;

/**
 * Created by bekir.cinar on 07/07/14.
 */
public class CardViewActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_view);

        CardView cardView = (CardView) findViewById(R.id.my_card_view2);

        //set radius programatically before sdk 21
        if (Build.VERSION.SDK_INT < 20) {
            cardView.setRadius(applyDimensionDpToFloat(4, getResources().getDisplayMetrics()));
        }
    }

    public static float applyDimensionDpToFloat(float value, DisplayMetrics metrics) {
        return value * metrics.density;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_info_text1:

                //shared item
                Intent intent = new Intent();
                intent.setClass(this, PhotoDetailActivity.class);
                ImageView hero = (ImageView) findViewById(R.id.my_image1);
                ((ViewGroup) hero.getParent()).setTransitionGroup(false);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, hero, "photo_hero");
                startActivity(intent, options.toBundle());
                break;
            case R.id.my_info_text2:
                break;
        }
    }
}
