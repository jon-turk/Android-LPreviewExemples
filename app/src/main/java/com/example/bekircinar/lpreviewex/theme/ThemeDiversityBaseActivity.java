package com.example.bekircinar.lpreviewex.theme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.example.bekircinar.lpreviewex.R;

/**
 * Created by bekir.cinar on 09/07/14.
 */
public abstract class ThemeDiversityBaseActivity extends Activity implements View.OnClickListener {

    protected void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.theme_alert_dialog_title));
        alertDialog.setMessage(getString(R.string.theme_alert_dialog_message));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "negative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "positive", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
