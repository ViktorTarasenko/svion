package omsu.svion.game.model;

import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

/**
 * Created by victor on 12.05.14.
 */
public class QuestionModel {
    public QuestionModel(Long id, byte[] image, String var1, String var2, String var3, String var4, long timeForAnswer, short correctAnswer, Theme theme, Cost cost) {
        this.id = id;
        this.image = image;
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.var4 = var4;
        this.timeForAnswer = timeForAnswer;
        this.correctAnswer = correctAnswer;
        this.theme = theme;
        this.cost = cost;
    }

    private Long id;
    private byte[] image;
    private String var1;
    private String var2;
    private String var3;
    private String var4;
    private long timeForAnswer;
    private short correctAnswer;
    private Theme theme;
    private Cost cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }

    public String getVar4() {
        return var4;
    }

    public void setVar4(String var4) {
        this.var4 = var4;
    }

    public long getTimeForAnswer() {
        return timeForAnswer;
    }

    public void setTimeForAnswer(long timeForAnswer) {
        this.timeForAnswer = timeForAnswer;
    }

    public short getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(short correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }
}
