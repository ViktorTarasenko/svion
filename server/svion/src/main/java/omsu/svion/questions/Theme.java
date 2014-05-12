package omsu.svion.questions;

/**
 * Created by victor on 11.05.14.
 */
public enum Theme {
    FASHION("МОДА",0),
    BOOKS_WORLD("КНИЖНЫЙ МИР",1),
    ALL_ABOUT_FASHION("ВСЕ О МОДЕ",2),
    ROUND_THE_WORLD("ВОКРУГ СВЕТА",3),
    FLOWERS("ЦВЕТЫ",4),
    PAROLE_OF_HONOR("ЧЕСТНОЕ СЛОВО",5);
    Theme(String russianName,int themeNumber) {
        this.russianName = russianName;
        this.themeNumber = themeNumber;
    }
    private final String russianName;
    private int themeNumber;

    public int getThemeNumber() {
        return themeNumber;
    }

    public String getRussianName() {
        return russianName;
    }
    public static Theme getByNumber(int themeNumber) {
        for (Theme theme : Theme.values()) {
            if (theme.getThemeNumber() == themeNumber) {
                return theme;
            }
        }
        return null;
    }
}
