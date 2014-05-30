package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import omsu.svion.MessagesHandlersResolver;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.handler.impl.ChooseThemeAndCostAnswerMessageHandler;
import omsu.svion.game.handler.impl.GameUpdateStateAnswerMessageHandler;
import omsu.svion.game.handler.impl.GameUpdateStateMessageWaitingHandler;
import omsu.svion.game.model.QuestionModel;
import omsu.svion.messages.AnswerMessage;
import omsu.svion.messages.ChooseThemeAndCostRequestMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.QuestionMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Andy on 11.04.14.
 */
public class QuestionActivity extends Activity
{
    public static QuestionActivity main;
    TextView[] playersNames = new TextView[3];
    TextView[] playersScores = new TextView[3];
    private Handler mHandler = new Handler();
    private RemainingTimeUpdater remainingTimeUpdater = new RemainingTimeUpdater();


    private TextView chosenAnswer;
    private TextView[] answers;

    public TextView getChosenAnswer() {
        return chosenAnswer;
    }

    public TextView[] getAnswers() {
        return answers;
    }

    public void setChosenAnswer(TextView chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main =this;
        refresh(getIntent());

    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refresh(intent);

    }
    @Override
    public void onBackPressed() {
        Websockethandler.closeWebsocket();
        if (SearchGame.main != null) {
            SearchGame.main.finish();
        }
        if (WaitingForEnoughPlayersActivity.main != null) {
            WaitingForEnoughPlayersActivity.main.finish();
        }
        if (GameActivity.main != null) {
            GameActivity.main.finish();
        }
        if (QuestionActivity.main != null) {
            QuestionActivity.main.finish();
        }
    }
    public void refresh(Intent intent) {
        setContentView(R.layout.question2);
        TextView[] answers = new TextView[]{
                (TextView)findViewById(R.id.answer1),
                (TextView)findViewById(R.id.answer2),
                (TextView)findViewById(R.id.answer3),
                (TextView)findViewById(R.id.answer4)
        };
        this.answers = answers;
       // for (TextView answer : answers) {
         //   answer.setTextAppearance(this,R.style.CostFont);
        //}
        setChosenAnswer(null);
        QuestionModel question = (QuestionModel) intent.getSerializableExtra("question");
        remainingTimeUpdater.setRemainingTime(question.getTimeForAnswer() / 1000);
        mHandler.postDelayed(remainingTimeUpdater,1000);
        answers[0].setText(question.getVar1());
        answers[1].setText(question.getVar2());
        answers[2].setText(question.getVar3());
        answers[3].setText(question.getVar4());
        final TextView text = (TextView) findViewById(R.id.questionText);
        text.setText(question.getText());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeByteArray(question.getImage(),0,question.getImage().length);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        imageView.setMinimumHeight(displayMetrics.heightPixels);
        imageView.setMinimumWidth(displayMetrics.widthPixels);
        imageView.setImageBitmap(bitmap);
        playersNames[0] = (TextView) findViewById(R.id.textView36);
        playersNames[1] = (TextView) findViewById(R.id.textView38);
        playersNames[2] = (TextView) findViewById(R.id.textView40);
        playersScores[0] = (TextView) findViewById(R.id.textView37);
        playersScores[1] = (TextView) findViewById(R.id.textView39);
        playersScores[2] = (TextView) findViewById(R.id.textView41);
        int[] players_scores = intent.getIntArrayExtra("playersScores");
        String[] players_names = intent.getStringArrayExtra("playersNames");
        for (int i = 0;i<playersNames.length;++i) {
            if (i < players_names.length) {
                playersNames[i].setText(players_names[i]);
                playersScores[i].setText(Integer.toString(players_scores[i]));
            }
            else {
                playersNames[i].setVisibility(View.GONE);
                playersScores[i].setVisibility(View.GONE);
            }
        }

        final QuestionActivity questionActivity = this;
        for (int i=0;i<answers.length;++i) {
            final int i1 = i + 1;
            final TextView[] answers1 = answers;
            answers[i].setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHandler.removeCallbacks(remainingTimeUpdater);
                            ObjectMapper objectMapper = new ObjectMapper();
                            AnswerMessage answerMessage = new AnswerMessage();
                            answerMessage.setVariant((short) i1);
                            ((TextView)v).setBackground(getResources().getDrawable(R.drawable.waiting_answer));
                            for (int j=0;j<answers1.length;++j) {
                                answers1[j].setEnabled(false);
                            }
                            try {
                                questionActivity.setChosenAnswer((TextView) v);
                                Websockethandler.getWebsocketClient().send(objectMapper.writeValueAsString(answerMessage));
                            } catch (IOException e) {
                                throw new RuntimeException();
                            }
                        }
                    }
            );
        }
        MessagesHandlersResolver.getHandlers().clear();
        MessagesHandlersResolver.getHandlers().put(ChooseThemeAndCostRequestMessage.class, new MessageFromServerHandler[]{new ChooseThemeAndCostAnswerMessageHandler(this)});
        MessagesHandlersResolver.getHandlers().put(GameStateUpdateMessage.class, new MessageFromServerHandler[]{new GameUpdateStateAnswerMessageHandler(this)});
    }
    public class RemainingTimeUpdater extends TimerTask {
        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        private long remainingTime;
        @Override
        public void run() {
            ((TextView)QuestionActivity.this.findViewById(R.id.textView)).setText("Время:"+remainingTime+"сек");
            remainingTime--;
            if (remainingTime >= 0) {
                mHandler.postDelayed(this,1000);
            }
        }
    }

}