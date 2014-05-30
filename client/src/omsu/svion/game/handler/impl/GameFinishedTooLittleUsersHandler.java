package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.games.Game;
import com.svion.client.*;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.states.Finished;
import omsu.svion.messages.GameFinishedTooLittleUsers;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 30.05.14.
 */
public class GameFinishedTooLittleUsersHandler implements MessageFromServerHandler {

    @Override
    public void handle(MessageFromServer message) {
        Log.d(" GameUpdateStateAnswerMessageHandler", "1");
        GameFinishedTooLittleUsers gameFinishedTooLittleUsers = (GameFinishedTooLittleUsers) message;
        Log.d(" GameUpdateStateAnswerMessageHandler", "2");
        Intent intent = new Intent(MyActivity.main, TooLittleUserActivity.class);
        String[] playersNames = new String[gameFinishedTooLittleUsers.getPlayers().size()];
        Log.d(" GameUpdateStateAnswerMessageHandler", "3");
        int[] playersScores = new int[gameFinishedTooLittleUsers.getPlayers().size()];
        PlayerModel winner = null;
        Log.d(" GameUpdateStateAnswerMessageHandler", "4");
        for (int i = 0; i < gameFinishedTooLittleUsers.getPlayers().size(); ++i) {
            playersNames[i] = gameFinishedTooLittleUsers.getPlayers().get(i).getEmail();
            playersScores[i] = gameFinishedTooLittleUsers.getPlayers().get(i).getScore();
            if ((winner == null) || (winner.getScore() <= gameFinishedTooLittleUsers.getPlayers().get(i).getScore())) {
                winner = gameFinishedTooLittleUsers.getPlayers().get(i);
            }
        }
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
        Log.d(" GameUpdateStateAnswerMessageHandler", "5");
        intent.putExtra("playersNames", playersNames);
        Log.d(" GameUpdateStateAnswerMessageHandler", "6");
        intent.putExtra("playersScores", playersScores);
        Log.d(" GameUpdateStateAnswerMessageHandler", "7");
        intent.putExtra("winner", winner.getEmail());
        MyActivity.main.startActivity(intent);
        Log.d(" GameUpdateStateAnswerMessageHandler", "8");
        Log.d(" GameUpdateStateAnswerMessageHandler", "9");
        Log.d(" GameUpdateStateAnswerMessageHandler", "10");
    }




}
