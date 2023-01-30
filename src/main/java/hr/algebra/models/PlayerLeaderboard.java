package hr.algebra.models;

import java.io.Serializable;

public class PlayerLeaderboard implements Serializable {
    PlayerDetails winner;
    PlayerDetails loser;
    long timeToVictory;

    public PlayerLeaderboard(PlayerDetails winner, PlayerDetails loser, long timeToVictory) {
        this.winner = winner;
        this.loser = loser;
        this.timeToVictory = timeToVictory;
    }

    @Override
    public String toString(){
        return winner + " has defeated " + loser + " in " + timeToVictory + " seconds.";
    }

    public double getTimeToVictory() {
        return timeToVictory;
    }


}
