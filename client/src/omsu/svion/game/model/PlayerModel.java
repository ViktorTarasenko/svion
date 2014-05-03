package omsu.svion.game.model;

/**
 * Created by victor on 12.04.14.
 */
public class PlayerModel {
    private int score;
    private String email;
    private PlayerState state;
    public PlayerModel() {

    }
    public PlayerModel(int score, String email, PlayerState state) {
        this.score = score;
        this.email = email;
        this.state = state;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
