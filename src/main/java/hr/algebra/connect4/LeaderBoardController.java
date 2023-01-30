package hr.algebra.connect4;

import hr.algebra.models.PlayerLeaderboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardController {

    @FXML
    private TextArea scoreBoard;

    public void displayData(List<PlayerLeaderboard> leaderboard){
        leaderboard.sort(Comparator.comparing(PlayerLeaderboard::getTimeToVictory));
        for(PlayerLeaderboard player : leaderboard){
            scoreBoard.appendText(player.toString() + "\n");
        }
    }

}
