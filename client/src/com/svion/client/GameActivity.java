package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import omsu.svion.MessagesHandlersResolver;


/**
 * Created by victor on 14.03.14.
 */
public class GameActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessagesHandlersResolver.getHandlers().clear();
        setContentView(R.layout.game);
       /* TextView[] themes = new TextView[]{
                (TextView)findViewById(R.id.textView),
                (TextView)findViewById(R.id.textView6),
                (TextView)findViewById(R.id.textView12),
                (TextView)findViewById(R.id.textView18),
                (TextView)findViewById(R.id.textView24),
                (TextView)findViewById(R.id.textView30)
        };*/

        TextView[] costs = new TextView[]{
                (TextView)findViewById(R.id.textView1),
                (TextView)findViewById(R.id.textView2),
                (TextView)findViewById(R.id.textView3),
                (TextView)findViewById(R.id.textView4),
                (TextView)findViewById(R.id.textView5),
                (TextView)findViewById(R.id.textView7),
                (TextView)findViewById(R.id.textView8),
                (TextView)findViewById(R.id.textView9),
                (TextView)findViewById(R.id.textView10),
                (TextView)findViewById(R.id.textView11),
                (TextView)findViewById(R.id.textView13),
                (TextView)findViewById(R.id.textView14),
                (TextView)findViewById(R.id.textView15),
                (TextView)findViewById(R.id.textView16),
                (TextView)findViewById(R.id.textView17),
                (TextView)findViewById(R.id.textView19),
                (TextView)findViewById(R.id.textView20),
                (TextView)findViewById(R.id.textView21),
                (TextView)findViewById(R.id.textView22),
                (TextView)findViewById(R.id.textView23),
                (TextView)findViewById(R.id.textView25),
                (TextView)findViewById(R.id.textView26),
                (TextView)findViewById(R.id.textView27),
                (TextView)findViewById(R.id.textView28),
                (TextView)findViewById(R.id.textView29),
                (TextView)findViewById(R.id.textView31),
                (TextView)findViewById(R.id.textView32),
                (TextView)findViewById(R.id.textView33),
                (TextView)findViewById(R.id.textView34),
                (TextView)findViewById(R.id.textView35)
        };

        TextView[] players_names = new TextView[]{
                (TextView)findViewById(R.id.textView36),
                (TextView)findViewById(R.id.textView38),
                (TextView)findViewById(R.id.textView40)
        };
        TextView[] players_scores = new TextView[]{
                (TextView)findViewById(R.id.textView37),
                (TextView)findViewById(R.id.textView39),
                (TextView)findViewById(R.id.textView41)
        };
        String[] playerNames = getIntent().getStringArrayExtra("playersNames");
        int[] playerScores = getIntent().getIntArrayExtra("playersScores");
        for (int i = 0;i<players_names.length;++i) {
            players_names[i].setText(playerNames[i % playerNames.length]);
        }
       //players_scores[0].setText(playerScores[0]);
        //players_scores[1].setText(playerScores[1]);
        //players_scores[2].setText(playerScores[2]);
        OnClickListener CostClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.setOnClickListener(null);
                startActivity(intent);
            }
        };

        for(int i=0;i<costs.length;i++)
        {
            costs[i].setOnClickListener(CostClick);
        }


    }
}