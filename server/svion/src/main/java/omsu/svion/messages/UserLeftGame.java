package omsu.svion.messages;

/**
 * Created by victor on 11.04.14.
 * used in ws servlet
 */
public class UserLeftGame extends GameManagementMessage {
    public UserLeftGame() {
        this.className = UserLeftGame.class.getCanonicalName();
    }
}
