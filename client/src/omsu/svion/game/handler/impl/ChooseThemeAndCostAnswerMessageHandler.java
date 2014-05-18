package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.util.Log;
import com.svion.client.GameActivity;
import com.svion.client.QuestionActivity;
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
    @Override
    public void handle(MessageFromServer message) {
        ChooseThemeAndCostRequestMessage chooseThemeAndCostRequestMessage = (ChooseThemeAndCostRequestMessage) message;
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

}
