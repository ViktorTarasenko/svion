package omsu.svion.game;

import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import omsu.svion.game.states.State;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.game.worker.GameThread;
import omsu.svion.messages.AbstractMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by victor on 05.04.14.
 */
public class Game {
    public Game(TaskExecutor taskExecutor,GameMessageHandlersResolver resolver) {
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
  //  private Class<? extends State> state = WaitingForEnoughPlayers.class;
    private GameThread gameThread;
    public boolean isReadyToStart(){
        return players.keySet().size() >= 3;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public synchronized Class<? extends State> getState() {
        if (states.size() > 0) {
            return states.peek();
        }
        return null;
    }

    public void destroy() {
        this.gameThread.interrupt();
        this.gameThread.clearMessages();
    }

}
