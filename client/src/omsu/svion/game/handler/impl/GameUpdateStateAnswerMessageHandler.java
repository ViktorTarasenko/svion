package omsu.svion.game.handler.impl;

import android.content.Intent;

import android.util.Log;
import android.widget.TextView;
import com.svion.client.GameFinishedActivity;
import com.svion.client.QuestionActivity;;
import com.svion.client.R;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.states.Finished;
import omsu.svion.messages.ChooseThemeAndCostRequestMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 27.05.14.
 */

public class GameUpdateStateAnswerMessageHandler implements MessageFromServerHandler {
    public GameUpdateStateAnswerMessageHandler(QuestionActivity activity) {
        this.activity = activity;
    }

    private QuestionActivity activity;
    private AnswerRightHandler answerRightHandler = new AnswerRightHandler();
    private AnswerWrongHandler answerWrongHandler = new AnswerWrongHandler();

    @Override
    public void handle(MessageFromServer message) {
        Log.d(" GameUpdateStateAnswerMessageHandler", "1");
        GameStateUpdateMessage gameStateUpdateMessage = (GameStateUpdateMessage) message;
        if (!gameStateUpdateMessage.getNewState().equals(Finished.class)) {
            return;
        }
        final QuestionActivity questionActivity = activity;
        final TextView rightAnswer = activity.getAnswers()[gameStateUpdateMessage.getIntegerParams().get("rightAnswer") - 1];
        final TextView chosenAnswer = activity.getChosenAnswer();
        Log.d("lololo", "1");
        if (chosenAnswer != null) {
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
        Log.d(" GameUpdateStateAnswerMessageHandler", "2");
        Intent intent = new Intent(activity, GameFinishedActivity.class);
        String[] playersNames = new String[gameStateUpdateMessage.getPlayers().size()];
        Log.d(" GameUpdateStateAnswerMessageHandler", "3");
        int[] playersScores = new int[gameStateUpdateMessage.getPlayers().size()];
        PlayerModel winner = null;
        Log.d(" GameUpdateStateAnswerMessageHandler", "4");
        for (int i = 0; i < gameStateUpdateMessage.getPlayers().size(); ++i) {
            playersNames[i] = gameStateUpdateMessage.getPlayers().get(i).getEmail();
            playersScores[i] = gameStateUpdateMessage.getPlayers().get(i).getScore();
            if ((winner == null) || (winner.getScore() <= gameStateUpdateMessage.getPlayers().get(i).getScore())) {
                winner = gameStateUpdateMessage.getPlayers().get(i);
            }
        }
        Log.d(" GameUpdateStateAnswerMessageHandler", "5");
        intent.putExtra("playersNames", playersNames);
        Log.d(" GameUpdateStateAnswerMessageHandler", "6");
        intent.putExtra("playersScores", playersScores);
        Log.d(" GameUpdateStateAnswerMessageHandler", "7");
        intent.putExtra("winner", winner.getEmail());
        Log.d(" GameUpdateStateAnswerMessageHandler", "8");
        activity.finish();
        Log.d(" GameUpdateStateAnswerMessageHandler", "9");
        activity.startActivity(intent);
        Log.d(" GameUpdateStateAnswerMessageHandler", "10");
    }

    private class AnswerRightHandler implements Runnable {
        @Override
        public void run() {
            GameUpdateStateAnswerMessageHandler.this.activity.getChosenAnswer().setBackground(GameUpdateStateAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.right_answer));
            synchronized (this) {
                ((Object) this).notify();
            }
        }
    }

    private class AnswerWrongHandler implements Runnable {

        public TextView getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(TextView rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        private TextView rightAnswer;

        @Override
        public void run() {
            GameUpdateStateAnswerMessageHandler.this.activity.getChosenAnswer().setBackground(GameUpdateStateAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.wrong_answer));
            rightAnswer.setBackground(GameUpdateStateAnswerMessageHandler.this.activity.getResources().getDrawable(R.drawable.right_answer));
            synchronized (this) {
                ((Object) this).notify();
            }
        }
    }
}
