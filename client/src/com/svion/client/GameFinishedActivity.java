package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by victor on 27.05.14.
 */
public class GameFinishedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(" GameFinishedActivity", "1");
        setContentView(R.layout.endgame);
        Log.d(" GameFinishedActivity", "2");
        Intent intent = getIntent();
        int[] scores = intent.getIntArrayExtra("playersScores");
        Log.d(" GameFinishedActivity", "3");
        String[] playerNames = intent.getStringArrayExtra("playersNames");
        Log.d(" GameFinishedActivity", "4");
        String winnerName = intent.getStringExtra("winner");
        Log.d(" GameFinishedActivity", "5");
        TextView[] players_scores = new TextView[]{(TextView) findViewById(R.id.textView7), (TextView) findViewById(R.id.textView8), (TextView) findViewById(R.id.textView9)};
        Log.d(" GameFinishedActivity", "6");
        TextView[] players_names = new TextView[]{(TextView) findViewById(R.id.textView2), (TextView) findViewById(R.id.textView3), (TextView) findViewById(R.id.textView4)};
        Log.d(" GameFinishedActivity", "7");
        TextView winner_name = (TextView) findViewById(R.id.textView5);
        for (int i = 0; i < players_names.length; ++i) {
            players_names[i].setText("");
            players_scores[i].setText("");
        }
        for (int i = 0; i < players_names.length; ++i) {
            if (i < playerNames.length) {
                players_names[i].setText(playerNames[i]);
                players_scores[i].setText(Integer.toString(scores[i]));
            }
            else {
                players_names[i].setVisibility(View.GONE);
                players_scores[i].setVisibility(View.GONE);
            }
        }
        Log.d(" GameFinishedActivity", "8");
        winner_name.setText("Победил "+winnerName+"!!!");
        GameActivity.main.finish();
        findViewById(R.id.textView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFinishedActivity.this.finish();
            }
        });
    }
}
