package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import com.svion.client.GameActivity;
import com.svion.client.QuestionActivity;
import com.svion.client.R;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.messages.ChooseThemeAndCostRequestMessage;
import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 17.05.14.
 */
public class ChooseThemeAndCostAnswerMessageHandler implements MessageFromServerHandler {
    public ChooseThemeAndCostAnswerMessageHandler(QuestionActivity activity) {
        this.activity = activity;
    }

    public QuestionActivity getActivity() {
        return activity;
    }

    public void setActivity(QuestionActivity activity) {
        this.activity = activity;
    }

    private QuestionActivity activity;
    private AnswerRightHandler answerRightHandler = new AnswerRightHandler();
    private AnswerWrongHandler answerWrongHandler = new AnswerWrongHandler();
    @Override
    public void handle(MessageFromServer message) {
        final QuestionActivity questionActivity = activity;
        ChooseThemeAndCostRequestMessage chooseThemeAndCostRequestMessage = (ChooseThemeAndCostRequestMessage) message;
        final TextView rightAnswer = activity.getAnswers()[chooseThemeAndCostRequestMessage.getRightAnswer() - 1];
        final TextView chosenAnswer = activity.getChosenAnswer();
        Log.d("lololo", "1");
        if (chosenAnswer !=null) {
        if (rightAnswer == chosenAnswer) {
            Log.d("lololo", "answer is correct");
            synchronized (answerRightHandler) {
                Log.d("lololo", "running update");
                activity.runOnUiThread(answerRightHandler);
                try {
                    ((Object) answerRightHandler).wait();
                    Log.d("lololo", "waiting finished");
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }

            }
        } else {
            Log.d("lololo", "answer is not correct");
            answerWrongHandler.setRightAnswer(rightAnswer);
            Log.d("lololo", "running update");
            synchronized (answerWrongHandler) {
                activity.runOnUiThread(answerWrongHandler);
                try {
                    ((Object) answerWrongHandler).wait();
                    Log.d("lololo", "waiting finished");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
      }



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        Intent intent = new Intent(activity, GameActivity.class);
        String[] playersNames = new String[3];
        int[] playersScores = new int[3];
        for (int i =0;i < chooseThemeAndCostRequestMessage.getPlayers().size();++i) {
            playersNames[i % 3] = chooseThemeAndCostRequestMessage .getPlayers().get(i).getEmail();
            playersScores[i % 3] = chooseThemeAndCostRequestMessage .getPlayers().get(i).getScore();
        }
        Log.d("dsa", "fullfilled players");
        intent.putExtra("playersNames", playersNames);
        intent.putExtra("playersScores",playersScores);
        intent.putExtra("availableThemesAndCosts", (java.io.Serializable) chooseThemeAndCostRequestMessage.getAvailableThemesAndCosts());
        intent.putExtra("answering",chooseThemeAndCostRequestMessage.isAnswer());
        intent.putExtra("answeringUser",chooseThemeAndCostRequestMessage.getAnsweringUser());
        Log.d("dsa","starting activity");
        Log.d("dsa",activity.toString());
        //activity.finish();
        activity.startActivity(intent);
        Log.d("dsa","started activity");


    }
    private class AnswerRightHandler implements Runnable{
        @Override
        public void run() {
            ChooseThemeAndCostAnswerMessageHandler.this.activity.getChosenAnswer().setBackground(ChooseThemeAndCostAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.right_answer));
            synchronized (this) {
                ((Object) this).notify();
            }
        }
    }
    private class AnswerWrongHandler implements Runnable{

        public TextView getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(TextView rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        private TextView rightAnswer;
        @Override
        public void run() {
             ChooseThemeAndCostAnswerMessageHandler.this.activity.getChosenAnswer().setBackground(ChooseThemeAndCostAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.wrong_answer));
             rightAnswer.setBackground(ChooseThemeAndCostAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.right_answer));
             synchronized(this) {
                 ((Object) this).notify();
             }
        }
    }

}
