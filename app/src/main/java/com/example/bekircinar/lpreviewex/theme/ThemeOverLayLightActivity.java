package com.example.bekircinar.lpreviewex.theme;

import android.os.Bundle;
import android.view.View;

import com.example.bekircinar.lpreviewex.R;

/**
 * Created by bekir.cinar on 09/07/14.
 */
public class ThemeOverLayLightActivity extends ThemeDiversityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theme_exemples);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_button_open_dialog:
                break;
        }
    }
}
