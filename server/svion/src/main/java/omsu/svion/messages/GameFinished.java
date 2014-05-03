package omsu.svion.messages;

/**
 * Created by victor on 11.04.14.
 */
public class GameFinished extends MessageFromServer{
    private Status status;
    public GameFinished(Status status){
        this.className = GameFinished.class.getCanonicalName();
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }
    public enum Status {
        NORMAL, TOO_LITTLE_USERS
    }
}
