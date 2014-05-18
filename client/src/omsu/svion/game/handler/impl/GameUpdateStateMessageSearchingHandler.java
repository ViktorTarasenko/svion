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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            Log.d("dsd","started activity");
            activity.finish();
        }
    }
}
