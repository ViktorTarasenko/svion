package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import com.svion.client.GameActivity;
import com.svion.client.R;
import com.svion.client.WaitingForEnoughPlayersActivity;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.states.ChoosingCategory;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 03.05.14.
 */
public class GameUpdateStateMessageWaitingHandler implements MessageFromServerHandler {
    public GameUpdateStateMessageWaitingHandler(WaitingForEnoughPlayersActivity activity) {
        this.activity = activity;
    }

    private WaitingForEnoughPlayersActivity activity;
    @Override
    public void handle(MessageFromServer message) {
        Log.d("I'm waiting","entered handler");
        final GameStateUpdateMessage gameStateUpdateMessage = (GameStateUpdateMessage)message;
        if (gameStateUpdateMessage.getNewState().equals(WaitingForEnoughPlayers.class)) {
            Log.d("I'm waiting","new players came");
            final TextView user1 = (TextView) activity.findViewById(R.id.player1);
            final TextView user2 = (TextView) activity.findViewById(R.id.player2);
            final TextView user3 = (TextView) activity.findViewById(R.id.player3);
            user1.post(new Runnable() {
                  @Override
                  public void run() {
                      user1.setText(gameStateUpdateMessage.getPlayers().size() > 0 ? gameStateUpdateMessage.getPlayers().get(0).getEmail() : null);
                 }
              });
            user2.post(new Runnable() {
                @Override
                public void run() {
                    user2.setText(gameStateUpdateMessage.getPlayers().size() > 1 ? gameStateUpdateMessage.getPlayers().get(1).getEmail() : null);
                }
            });
            user3.post(new Runnable() {
                @Override
                public void run() {
                    user3.setText(gameStateUpdateMessage.getPlayers().size() > 2 ? gameStateUpdateMessage.getPlayers().get(2).getEmail() : null);
                }
            });

        }

    }
}
