package hr.algebra.models;

import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.List;

public class SerializableCircle implements Serializable {
    private int xLocation;
    private int yLocation;
    private PlayerDetails owner;

    private PlayerDetails playerOne;
    private PlayerDetails playerTwo;

    private List<PlayerLeaderboard> leaderboard;

    private boolean firstPlayerTurn;


    public SerializableCircle(int xLocation, int yLocation, PlayerDetails owner) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.owner = owner;
    }

    public List<PlayerLeaderboard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<PlayerLeaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public PlayerDetails getPlayerOne(){
        return playerOne;
    }

    public PlayerDetails getPlayerTwo(){
        return playerTwo;
    }

    public void setPlayerOne(PlayerDetails player){
        playerOne = player;
    }

    public void setPlayerTwo(PlayerDetails player){
        playerTwo = player;
    }
    public int getxLocation() {
        return xLocation;
    }


    public int getyLocation() {
        return yLocation;
    }

    public boolean getPlayerTurn() {return firstPlayerTurn;}

    public void setPlayerTurn(boolean turn){firstPlayerTurn = turn;}

    public PlayerDetails getOwner() {
        return owner;
    }

}
