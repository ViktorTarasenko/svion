package com.svion.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import omsu.svion.MessagesHandlersResolver;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.handler.impl.GameUpdateStateMessageWaitingHandler;
import omsu.svion.game.handler.impl.QuestionMessageHandler;
import omsu.svion.messages.ChooseThemeAndCostMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.QuestionMessage;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by victor on 14.03.14.
 */
public class GameActivity extends Activity {
    public static GameActivity main;
    public Map<Pair<Theme, Cost>, TextView> getTextViews() {
        return textViews;
    }
    private TextView[] costs;

    public TextView[] getCosts() {
        return costs;
    }

    public void setCosts(TextView[] costs) {
        this.costs = costs;
    }

    private Map<Pair<Theme,Cost>,TextView> textViews = new HashMap<Pair<Theme, Cost>, TextView>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        refresh(getIntent());

    }
    @Override
    public void onNewIntent(Intent intent) {
       super.onNewIntent(intent);
        refresh(intent);

    }
    public  void refresh(Intent intent) {
        MessagesHandlersResolver.getHandlers().clear();
        setContentView(R.layout.game);
        TextView[] themes = new TextView[]{
                (TextView)findViewById(R.id.textView),
                (TextView)findViewById(R.id.textView6),
                (TextView)findViewById(R.id.textView12),
                (TextView)findViewById(R.id.textView18),
                (TextView)findViewById(R.id.textView24),
                (TextView)findViewById(R.id.textView30)
        };
        TextView[] costs =  new TextView[]{
                (TextView)findViewById(R.id.textView1),
                (TextView)findViewById(R.id.textView2),
                (TextView)findViewById(R.id.textView3),
                (TextView)findViewById(R.id.textView4),
                (TextView)findViewById(R.id.textView5),
                (TextView)findViewById(R.id.textView7),
                (TextView)findViewById(R.id.textView8),
                (TextView)findViewById(R.id.textView9),
                (TextView)findViewById(R.id.textView10),
                (TextView)findViewById(R.id.textView11),
                (TextView)findViewById(R.id.textView13),
                (TextView)findViewById(R.id.textView14),
                (TextView)findViewById(R.id.textView15),
                (TextView)findViewById(R.id.textView16),
                (TextView)findViewById(R.id.textView17),
                (TextView)findViewById(R.id.textView19),
                (TextView)findViewById(R.id.textView20),
                (TextView)findViewById(R.id.textView21),
                (TextView)findViewById(R.id.textView22),
                (TextView)findViewById(R.id.textView23),
                (TextView)findViewById(R.id.textView25),
                (TextView)findViewById(R.id.textView26),
                (TextView)findViewById(R.id.textView27),
                (TextView)findViewById(R.id.textView28),
                (TextView)findViewById(R.id.textView29),
                (TextView)findViewById(R.id.textView31),
                (TextView)findViewById(R.id.textView32),
                (TextView)findViewById(R.id.textView33),
                (TextView)findViewById(R.id.textView34),
                (TextView)findViewById(R.id.textView35)
        };
        this.costs = costs;
        for (TextView cost : costs) {
          cost.setTextAppearance(this,R.style.CostFont);
            cost.setText("");
        }
        for (TextView theme : themes) {
            theme.setTextAppearance(this,R.style.CostFont);
            theme.setText("");
        }


        TextView[] players_names = new TextView[]{
                (TextView)findViewById(R.id.textView36),
                (TextView)findViewById(R.id.textView38),
                (TextView)findViewById(R.id.textView40)
        };
        TextView[] players_scores = new TextView[]{
                (TextView)findViewById(R.id.textView37),
                (TextView)findViewById(R.id.textView39),
                (TextView)findViewById(R.id.textView41)
        };
        String[] playerNames = intent.getStringArrayExtra("playersNames");
        int[] playerScores = intent.getIntArrayExtra("playersScores");
        Map<Theme,List<Cost>> availableThemesAndCosts = (Map<Theme, List<Cost>>) intent.getSerializableExtra("availableThemesAndCosts");
        for (int i = 0;i<players_names.length;++i) {
            players_names[i].setText(playerNames[i % playerNames.length]);
        }
        for (int i = 0;i<players_scores.length;++i) {
            players_scores[i].setText(Integer.toString(playerScores[i % playerScores.length]));
        }
        int i=0,j;
        for (Theme theme : availableThemesAndCosts.keySet()) {
            themes[i].setText(theme.getRussianName());
            j = 0;
            for (Cost cost : availableThemesAndCosts.get(theme)) {
                costs[i*5+j].setText(Integer.toString(cost.getCost()));
                textViews.put(new Pair<Theme, Cost>(theme,cost),costs[i*5+j]);
                ++j;
            }
            ++i;
        }
        MessagesHandlersResolver.getHandlers().clear();
        MessagesHandlersResolver.getHandlers().put(QuestionMessage.class, new MessageFromServerHandler[]{new QuestionMessageHandler(this)});
        MessagesHandlersResolver.getHandlers().put(QuestionMessage.class, new MessageFromServerHandler[]{new QuestionMessageHandler(this)});
        boolean answering =  intent.getBooleanExtra("answering",false);
        Log.d("answering",answering+"");
        String answeringUser = intent.getStringExtra("answeringUser");
        Log.d("answering user",answeringUser);
        TextView whoAnswersDisplay = (TextView) findViewById(R.id.textView42);
        if (answering) {
            Log.d("answering user","1");
            whoAnswersDisplay.setText("Выберите тему и стоимость вопроса!");
            Log.d("answering user","2");
            i = 0;
            for (final Theme theme : availableThemesAndCosts.keySet()) {
                Log.d("answering user","2");
                j = 0;
                for (final Cost cost : availableThemesAndCosts.get(theme)) {
                    Log.d("answering user","3");
                    costs[i * 5 + j].setOnClickListener(
                            new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("click","clicked theme "+theme+" cost "+cost);
                                    v.setBackground(GameActivity.this.getResources().getDrawable(R.drawable.right_answer));
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    ChooseThemeAndCostMessage chooseThemeAndCostMessage = new ChooseThemeAndCostMessage(cost, theme);
                                    try {
                                        Websockethandler.getWebsocketClient().send(objectMapper.writeValueAsString(chooseThemeAndCostMessage));
                                    } catch (IOException e) {
                                        throw new RuntimeException();
                                    }
                                }
                            }
                    );
                    Log.d("answering user","4");

                    ++j;
                }
                ++i;
            }
        }
        else {
            Log.d("not answering user","1");
            whoAnswersDisplay.setText("Игрок "+answeringUser+" выбирает тему и стоимость");
            Log.d("not answering user","1");
        }
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
}