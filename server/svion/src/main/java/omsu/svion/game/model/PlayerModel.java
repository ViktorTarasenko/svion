package omsu.svion.game.model;

import omsu.svion.game.Player;

/**
 * Created by victor on 12.04.14.
 */
public class PlayerModel {
    private int score;
    private String email;
    private Player.State state;

    public PlayerModel(int score, String email, Player.State state) {
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

    public Player.State getState() {
        return state;
    }

    public void setState(Player.State state) {
        this.state = state;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
