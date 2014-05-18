package com.svion.client;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import omsu.svion.MessagesHandlersResolver;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.handler.impl.ChooseThemeAndCostRequestWaitingHandler;
import omsu.svion.game.handler.impl.ChooseThemeAndCostSearchingHandler;
import omsu.svion.game.handler.impl.GameUpdateStateMessageWaitingHandler;
import omsu.svion.messages.ChooseThemeAndCostRequestMessage;
import omsu.svion.messages.GameStateUpdateMessage;

/**
 * Created by victor on 02.05.14.
 */
public class WaitingForEnoughPlayersActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_enough_players);
        Log.d("act","starting");
        String[] players = getIntent().getStringArrayExtra("players");
        TextView user1 = (TextView) findViewById(R.id.player1);
        TextView user2 = (TextView) findViewById(R.id.player2);
        TextView user3 = (TextView) findViewById(R.id.player3);
        user1.setText(players[0]);
        user2.setText(players[1]);
        user3.setText(players[2]);
        MessagesHandlersResolver.getHandlers().clear();
        MessagesHandlersResolver.getHandlers().put(GameStateUpdateMessage.class, new MessageFromServerHandler[]{new GameUpdateStateMessageWaitingHandler(this)});
        MessagesHandlersResolver.getHandlers().put(ChooseThemeAndCostRequestMessage.class, new MessageFromServerHandler[]{new ChooseThemeAndCostRequestWaitingHandler(this)});
        Log.d("act","ok");

    }
}
