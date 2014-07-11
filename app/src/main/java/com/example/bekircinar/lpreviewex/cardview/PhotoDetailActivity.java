package com.example.bekircinar.lpreviewex.cardview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bekircinar.lpreviewex.R;

/**
 * Created by bekir.cinar on 10/07/14.
 */
public class PhotoDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_detail);

        ((ImageView) findViewById(R.id.photo)).setImageDrawable(getResources().getDrawable(R.drawable.dog));

    }
}