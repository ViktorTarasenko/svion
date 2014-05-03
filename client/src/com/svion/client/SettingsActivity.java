package com.svion.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.view.HapticFeedbackConstants;
import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by Andy on 07.04.14.
 */

public class SettingsActivity extends Activity{

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "mysettings";

    public static final Integer APP_PREFERENCES_Sound = 1;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void settings_back_click(View v){
        //TextView settings_back = (TextView) findViewById(R.id.settings); // обратная связь
        //settings_back.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent intent = new Intent(this, MyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //moveTaskToBack(true);
        //super.onBackPressed();
    }
}
