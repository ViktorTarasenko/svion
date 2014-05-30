package com.svion.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by victor on 30.05.14.
 */
public class ConnectionLostActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_lost);
        findViewById(R.id.textView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionLostActivity.this.finish();
            }
        });
    }
}
