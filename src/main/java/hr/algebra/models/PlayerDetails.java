package hr.algebra.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PlayerDetails implements Serializable {
    private String playerName;

    public boolean firstPlayer;
    private List<String> moves;

    public void setMoves(int row, int column) {
        moves.add(row + " " + column);
    }

    public void resetMoves(){this.moves = new ArrayList<>();}
    public PlayerDetails(String playerName) {
        this.playerName = playerName; this.moves = new ArrayList<>();
        ;
    }
    public String getPlayerName() {
        return playerName;
    }

    public List<String> getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return playerName;
    }

    @Override
    public boolean equals(Object obj) {
        PlayerDetails details = (PlayerDetails)obj;
        if (details == null){
            return false;
        }
        return this.playerName.equals(details.playerName);
    }
}


