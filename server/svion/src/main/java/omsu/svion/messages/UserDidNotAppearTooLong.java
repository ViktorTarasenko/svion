package omsu.svion.messages;

/**
 * Created by victor on 11.04.14.
 * called by scheduler, which is scheduled if user occasionally disconnected
 */
public class UserDidNotAppearTooLong extends GameManagementMessage {
    public UserDidNotAppearTooLong() {
        this.className = UserDidNotAppearTooLong.class.getCanonicalName();
    }

}
