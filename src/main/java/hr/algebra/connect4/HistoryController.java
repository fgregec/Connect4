package hr.algebra.connect4;

import hr.algebra.models.PlayerDetails;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class HistoryController {

    @FXML
    public TextArea historyBoard;

    public void displayData(PlayerDetails playerOne, PlayerDetails playerTwo) {
        List<String> playerOneMoves = playerOne.getMoves();
        List<String> playerTwoMoves = playerTwo.getMoves();
        historyBoard.appendText(playerOneMoves.toString());
        historyBoard.appendText("\n" + playerTwoMoves.toString());
    }
}
