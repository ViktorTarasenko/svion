package omsu.svion.entities;

import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

import javax.persistence.*;


/**
 * Created by victor on 11.05.14.
 */
@Entity
@Table(name = "question",indexes = {@Index(name = "cost",columnList = "cost"),@Index(name = "theme",columnList = "theme")})
@NamedQueries({
        @NamedQuery(name = Question.GET_COUNT_OF_QUESTIONS,query = "select count(q.id) from Question q where q.cost = :cost and q.theme = :theme"),
        @NamedQuery(name = Question.FIND_BY_THEME_AND_COST,query = "select q from Question q where q.cost = :cost and q.theme = :theme "),
        @NamedQuery(name = Question.FIND_BY_THEME_AND_COST_EXCLUDE,query = "select q from Question q where q.cost = :cost and q.theme = :theme and  q.id not in (:alreadyUsedIds)"),
        @NamedQuery(name = Question.GET_COUNTS_OF_NOT_USED_QUESTIONS,query = "select count(*) from Question q where q.cost = :cost and q.theme = :theme and  q.id not in (:alreadyUsedIds)"),


})
public class Question extends AbstractEntity{
    public static final String GET_COUNT_OF_QUESTIONS = "GET_COUNT_OF_QUESTIONS";
    public static final String FIND_BY_THEME_AND_COST = "FIND_QUESTION_BY_THEME_AND_COST";
    public static final String GET_COUNTS_OF_NOT_USED_QUESTIONS = "GET_COUNTS_OF_NOT_USED_QUESTIONS";
    public static final String FIND_BY_THEME_AND_COST_EXCLUDE = "FIND_QUESTION_BY_THEME_AND_COST_EXCLUDE";
    @Column(name = "text",columnDefinition = "TEXT",nullable = false)
    private String text;
    @Column(name = "image",columnDefinition = "TEXT",nullable = false)
    private String image;
    @Column(name = "var1",columnDefinition = "TEXT",nullable = false)
    private String var1;
    @Column(name = "var2",columnDefinition = "TEXT",nullable = false)
    private String var2;
    @Column(name = "var3",columnDefinition = "TEXT",nullable = false)
    private String var3;
    @Column(name = "var4",columnDefinition = "TEXT",nullable = false)
    private String var4;
    @Column(name="time_for_answer",nullable = false)
    private long timeForAnswer;
    @Column(name = "correct_answer",nullable = false)
    private short correctAnswer;
    @Column(name="theme",nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme;
    @Column(name="cost",nullable = false)
    @Enumerated(EnumType.STRING)
    private Cost cost;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
