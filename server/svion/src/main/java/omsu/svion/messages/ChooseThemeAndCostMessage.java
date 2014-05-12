package omsu.svion.messages;

import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

/**
 * Created by victor on 11.05.14.
 */
public class ChooseThemeAndCostMessage extends MessageFromClient{
    public ChooseThemeAndCostMessage(Cost cost, Theme theme) {
        this.cost = cost;
        this.theme = theme;
        this.className = ChooseThemeAndCostMessage.class.getCanonicalName();
    }
    private Cost cost;
    private Theme theme;

    public Cost getCost() {
        return cost;
    }

    public Theme getTheme() {
        return theme;
    }
}
