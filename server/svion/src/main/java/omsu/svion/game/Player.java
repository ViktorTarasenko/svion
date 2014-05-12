package omsu.svion.game;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by victor on 05.04.14.
 */
public class Player {
    public Player(String email, WebSocketSession session, int score) {
        this.email = email;
        this.webSocketSession = session;
        this.score = score;
    }
    private Runnable disconnectedScheduler;

    public Runnable getDisconnectedScheduler() {
        return disconnectedScheduler;
    }

    public void setDisconnectedScheduler(Runnable disconnectedScheduler) {
        this.disconnectedScheduler = disconnectedScheduler;
    }
    private boolean answeredQuestion;
    private State state = State.ONLINE;
    private int score = 0;
    private Game game;
    private String email;
    private WebSocketSession webSocketSession;
    int incrementScore;

    public int getIncrementScore() {
        return incrementScore;
    }

    public void setIncrementScore(int incrementScore) {
        this.incrementScore = incrementScore;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public synchronized void  setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public String getEmail() {
        return email;
    }

    public synchronized void setEmail(String email) {
        this.email = email;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public synchronized void  setGame() {
        this.game = game;
    }
    public enum State {
        ONLINE, OCCASIONALLY_DISCONNECTED;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(boolean answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }
}
