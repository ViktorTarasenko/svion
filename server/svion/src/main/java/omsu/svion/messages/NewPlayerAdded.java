package omsu.svion.messages;

/**
 * Created by victor on 11.04.14.
 */
public class NewPlayerAdded extends GameManagementMessage {
    public NewPlayerAdded() {
        this.className = NewPlayerAdded.class.getCanonicalName();
    }
}
