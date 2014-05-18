package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import omsu.svion.MessagesHandlersResolver;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.handler.impl.ChooseThemeAndCostAnswerMessageHandler;
import omsu.svion.game.handler.impl.GameUpdateStateMessageWaitingHandler;
import omsu.svion.game.model.QuestionModel;
import omsu.svion.messages.AnswerMessage;
import omsu.svion.messages.ChooseThemeAndCostRequestMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.QuestionMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Andy on 11.04.14.
 */
public class QuestionActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refresh(getIntent());

    }
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refresh(intent);

    }
    public void refresh(Intent intent) {
        setContentView(R.layout.question);
        TextView[] answers = new TextView[]{
                (TextView)findViewById(R.id.answer1),
                (TextView)findViewById(R.id.answer2),
                (TextView)findViewById(R.id.answer3),
                (TextView)findViewById(R.id.answer4)
        };
        QuestionModel question = (QuestionModel) intent.getSerializableExtra("question");
        answers[0].setText(question.getVar1());
        answers[1].setText(question.getVar2());
        answers[2].setText(question.getVar3());
        answers[3].setText(question.getVar4());
        TextView text = (TextView) findViewById(R.id.questionText);
        text.setText(question.getText());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeByteArray(question.getImage(),0,question.getImage().length);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        imageView.setMinimumHeight(displayMetrics.heightPixels);
        imageView.setMinimumWidth(displayMetrics.widthPixels);
        imageView.setImageBitmap(bitmap);
        for (int i=0;i<answers.length;++i) {
            final int i1 = i + 1;
            final TextView[] answers1 = answers;
            answers[i].setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            AnswerMessage answerMessage = new AnswerMessage();
                            answerMessage.setVariant((short) i1);
                            try {
                                Websockethandler.getWebsocketClient().send(objectMapper.writeValueAsString(answerMessage));
                                for (int i=0;i<answers1.length;++i) {
                                    answers1[i].setEnabled(false);
                                }
                                ((TextView)v).setBackgroundColor(Color.YELLOW);
                            } catch (IOException e) {
                                throw new RuntimeException();
                            }
                        }
                    }
            );
        }
        MessagesHandlersResolver.getHandlers().clear();
        MessagesHandlersResolver.getHandlers().put(ChooseThemeAndCostRequestMessage.class, new MessageFromServerHandler[]{new ChooseThemeAndCostAnswerMessageHandler(this)});
    }

}