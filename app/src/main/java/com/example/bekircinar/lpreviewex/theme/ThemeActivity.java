package com.example.bekircinar.lpreviewex.theme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bekircinar.lpreviewex.R;
import com.example.bekircinar.lpreviewex.theme.ThemeDefaultActivity;
import com.example.bekircinar.lpreviewex.theme.ThemeLightActivity;
import com.example.bekircinar.lpreviewex.theme.ThemeLightDarkActionBarActivity;
import com.example.bekircinar.lpreviewex.theme.ThemeNoActionBarActivity;
import com.example.bekircinar.lpreviewex.theme.ThemeOverLayLightActivity;

/**
 * Created by bekir.cinar on 08/07/14.
 */
public class ThemeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theme);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_button_default:
                startActivity(new Intent(this, ThemeDefaultActivity.class));
                break;
            case R.id.my_button_light:
                startActivity(new Intent(this, ThemeLightActivity.class));
                break;
            case R.id.my_button_light_dark_bar:
                startActivity(new Intent(this, ThemeLightDarkActionBarActivity.class));
                break;
            case R.id.my_button_overlay_dark:
                startActivity(new Intent(this, ThemeLightActivity.class));
                break;
            case R.id.my_button_no_action_bar:
                startActivity(new Intent(this, ThemeNoActionBarActivity.class));
                break;
        }
    }
}
