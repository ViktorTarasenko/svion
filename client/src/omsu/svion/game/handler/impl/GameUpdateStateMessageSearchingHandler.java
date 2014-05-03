package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import com.svion.client.GameActivity;
import com.svion.client.R;
import com.svion.client.SearchGame;
import com.svion.client.WaitingForEnoughPlayersActivity;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.states.ChoosingCategory;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 03.05.14.
 */
public class GameUpdateStateMessageSearchingHandler implements MessageFromServerHandler {
    public GameUpdateStateMessageSearchingHandler(SearchGame activity) {
        this.activity = activity;
    }

    private SearchGame activity;
    @Override
    public void handle(MessageFromServer message) {
        Log.d("dsd","entered handler");
        GameStateUpdateMessage gameStateUpdateMessage = (GameStateUpdateMessage)message;
        Log.d("dsd",gameStateUpdateMessage.toString());
        if (gameStateUpdateMessage.getNewState().equals(WaitingForEnoughPlayers.class)) {
            Log.d("dsd","waiting for enough players");
            Intent intent = new Intent(activity, WaitingForEnoughPlayersActivity.class);
            String[] players = new String[3];
            for (int i =0;i < gameStateUpdateMessage.getPlayers().size();++i) {
                players[i % 3] = gameStateUpdateMessage.getPlayers().get(i).getEmail();
            }
            Log.d("dsd","fullfilled players");
            intent.putExtra("players",players);
            Log.d("dsd","starting activity");
            Log.d("dsd",activity.toString());
            activity.startActivity(intent);
            Log.d("dsd","started activity");
            activity.finish();
        }
         else if (gameStateUpdateMessage.getNewState().equals(ChoosingCategory.class)){
            Intent intent = new Intent(activity, GameActivity.class);
            String[] playersNames = new String[3];
            int[] playersScores = new int[3];
            for (int i =0;i < gameStateUpdateMessage.getPlayers().size();++i) {
                playersNames[i % 3] = gameStateUpdateMessage.getPlayers().get(i).getEmail();
            }
            for (int i =0;i < gameStateUpdateMessage.getPlayers().size();++i) {
                playersScores[i % 3] = gameStateUpdateMessage.getPlayers().get(i).getScore();
            }
            Log.d("dsa", "fullfilled players");
            intent.putExtra("playersNames",playersNames);
            intent.putExtra("playersScores",playersScores);
            Log.d("dsa","starting activity");
            Log.d("dsa",activity.toString());
            activity.startActivity(intent);
            Log.d("dsa","started activity");
            activity.finish();
           }
    }
}
