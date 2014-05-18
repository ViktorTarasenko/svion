package omsu.svion.game;

import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import omsu.svion.game.model.QuestionModel;
import omsu.svion.game.states.State;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.game.worker.GameThread;
import omsu.svion.messages.AbstractMessage;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by victor on 05.04.14.
 */
public class Game {
    private Runnable costAndThemeChosenChecker;
    private Runnable everybodyAnsweredChecker;
    private Player previousAnsweredPlayer;

    public Game(TaskExecutor taskExecutor,GameMessageHandlersResolver resolver) {
        for (int i=0;i<3;++i) {
           availableCostsAndThemes.add(i,new HashMap<Theme, List<Cost>>());
        }
        List<Cost> costsForFirstRound = new ArrayList<Cost>(5);
        List<Cost> costsForSecondRound = new ArrayList<Cost>(5);
        List<Cost> costsForThirdRound = new ArrayList<Cost>(5);
        for (int i =0, firstCost=100,secondCost = 200,thirdCost = 300;i<5;++i,firstCost+=100,secondCost +=200, thirdCost += 300) {
            costsForFirstRound.add(i, Cost.getByCost(firstCost));
            costsForSecondRound.add(i,Cost.getByCost(secondCost));
            costsForThirdRound.add(i,Cost.getByCost(thirdCost));
        }
        for (Theme theme : Theme.values()) {
                availableCostsAndThemes.get(0).put(theme,new ArrayList<Cost>(costsForFirstRound));
                availableCostsAndThemes.get(1).put(theme, new ArrayList<Cost>(costsForSecondRound));
                availableCostsAndThemes.get(2).put(theme, costsForThirdRound);
        }
        setState(WaitingForEnoughPlayers.class);
        gameThread = new GameThread(resolver,this);
        taskExecutor.execute(gameThread);
    }
    public void  handleMessage(AbstractMessage message) {
        gameThread.addMesssage(message);
    }
    public  synchronized void setState(final Class<? extends State> state ) {
     states.clear();
     states.push(state);
    }
    public  synchronized Class<? extends State> restoreState() {
        if (states.size() >= 2) {
            return states.pop();
        }
        return null;
    }
    public  synchronized void pushState(Class<? extends State> state) {
        states.push(state);
    }
    public Player getPlayer(String email) {
        return players.get(email);
    }
    public void addPlayer(String email, Player player) {
        players.put(email,player);
    }
    public void removePlayer(String email) {
        players.remove(email);
    }
    private Map<String,Player> players = new ConcurrentHashMap<String, Player>();
    private Stack<Class<? extends State>> states = new Stack<Class<? extends State>>();
    private List<Long> usedQuestionsIds = new ArrayList<Long>(45);
    private ArrayList<Map<Theme,List<Cost>>> availableCostsAndThemes = new ArrayList<Map<Theme, List<Cost>>>(3);
    private int tourNumber = 1;
    private  int currentQuestionNumber = 0;
    private QuestionModel currentQuestion;
    private GameThread gameThread;
    public boolean isReadyToStart(){
        return players.keySet().size() >= 2;
    }

    public List<Long> getUsedQuestionsIds() {
        return usedQuestionsIds;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public ArrayList<Map<Theme, List<Cost>>> getAvailableCostsAndThemes() {
        return availableCostsAndThemes;
    }

    public void setAvailableCostsAndThemes(ArrayList<Map<Theme, List<Cost>>> availableCostsAndThemes) {
        this.availableCostsAndThemes = availableCostsAndThemes;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }



    public synchronized Class<? extends State> getState() {
        if (states.size() > 0) {
            return states.peek();
        }
        return null;
    }

    public int getTourNumber() {
        return tourNumber;
    }

    public void setTourNumber(int tourNumber) {
        this.tourNumber = tourNumber;
    }

    public void destroy() {
        this.gameThread.interrupt();
        this.gameThread.clearMessages();
    }

    public Runnable getCostAndThemeChosenChecker() {
        return costAndThemeChosenChecker;
    }

    public void setCostAndThemeChosenChecker(Runnable costAndThemeChosenChecker) {
        this.costAndThemeChosenChecker = costAndThemeChosenChecker;
    }

    public QuestionModel getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionModel currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Runnable getEverybodyAnsweredChecker() {
        return everybodyAnsweredChecker;
    }

    public void setEverybodyAnsweredChecker(Runnable everybodyAnsweredChecker) {
        this.everybodyAnsweredChecker = everybodyAnsweredChecker;
    }

    public void setPreviousAnsweredPlayer(Player previousAnsweredPlayer) {
        this.previousAnsweredPlayer = previousAnsweredPlayer;
    }

    public Player getPreviousAnsweredPlayer() {
        return previousAnsweredPlayer;
    }
}
