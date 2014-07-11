package com.example.bekircinar.lpreviewex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bekircinar.lpreviewex.animation.AnimationActivity;
import com.example.bekircinar.lpreviewex.cardview.CardViewActivity;
import com.example.bekircinar.lpreviewex.recyclerview.RecyclerActivity;
import com.example.bekircinar.lpreviewex.theme.ThemeActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_button_recycler_view:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            case R.id.my_button_card_view:
                startActivity(new Intent(this, CardViewActivity.class));
                break;
            case R.id.my_button_themes:
                startActivity(new Intent(this, ThemeActivity.class));
                break;
            case R.id.my_button_animation:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
        }
    }

}
