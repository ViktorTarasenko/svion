package omsu.svion.questions;

/**
 * Created by victor on 11.05.14.
 */
public enum  Cost {
    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    THREE_HUNDREDS(300),
    FOUR_HUNDREDS(400),
    FIVE_HUNDREDS(500),
    SIX_HUNDREDS(600),
    EIGHT_HUNDREDS(800),
    NINE_HUNDREDS(900),
    ONE_THOUSAND(1000),
    ONE_THOUSAND_TWO_HUNDREDS(1200),
    ONE_THOUSAND_FIVE_HUNDREDS(1500);
    Cost(final int cost) {
        this.cost = cost;
    }
    private final int cost;

    public int getCost() {
        return cost;
    }
    public static Cost getByCost(int cost) {
        for (Cost cost1 : values()) {
            if (cost1.getCost() == cost)
                return cost1;
        }
        return null;
    }
}
