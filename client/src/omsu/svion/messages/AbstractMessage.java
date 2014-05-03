package omsu.svion.messages;


/**
 * Created by victor on 09.04.14.
 */
public class AbstractMessage {
    public Object getSession() {
        return session;
    }

    public void setSession(Object session) {
        this.session = session;
    }

    protected Object session = null;

    protected String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
