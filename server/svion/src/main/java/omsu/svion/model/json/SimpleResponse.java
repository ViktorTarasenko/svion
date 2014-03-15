package omsu.svion.model.json;

/**
 * Created by victor on 12.03.14.
 */
public class SimpleResponse {
    private String text;

    public SimpleResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
