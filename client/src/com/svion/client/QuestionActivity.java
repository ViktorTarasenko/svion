package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * Created by Andy on 11.04.14.
 */
public class QuestionActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        TextView[] answers = new TextView[]{
                (TextView)findViewById(R.id.answer1),
                (TextView)findViewById(R.id.answer2),
                (TextView)findViewById(R.id.answer3),
                (TextView)findViewById(R.id.answer4)
        };

        OnClickListener AnswerClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this, "Нажали на " + findViewById(v.getId()), Toast.LENGTH_SHORT).show();
            }
        };

        for(int i=0;i<answers.length;i++)
        {
            answers[i].setOnClickListener(AnswerClick);
        }
    }
}