package omsu.svion.messages;

/**
 * Created by victor on 11.04.14.
 * used in websocketservlet
 */
public class UserOccasionallyDisconnected extends GameManagementMessage {
    public UserOccasionallyDisconnected() {
        this.className = UserOccasionallyDisconnected.class.getCanonicalName();
    }
}
