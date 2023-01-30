package hr.algebra.connect4;


import hr.algebra.models.PlayerDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GameSetupController {

    @FXML
    private Label lbNameError;
    @FXML
    private TextField playerOneName;
    @FXML
    private TextField playerTwoName;
    @FXML
    private Button btnStartGame;

    private PlayerDetails playerOne;
    private PlayerDetails playerTwo;

    private Parent root;

    public void onStartClick() throws IOException {
        if(!playerNamesValid()){
            lbNameError.setText("Check inserted names!");
            return;
        }
        lbNameError.setText("");
        playerOne = new PlayerDetails(playerOneName.getText());
        playerTwo = new PlayerDetails(playerTwoName.getText());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainScreen.fxml"));
        Scene scene = null;

        try{
            scene = new Scene(fxmlLoader.load(), 600,400);
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        GameController gameController = fxmlLoader.getController();
        gameController.preparePlayers(playerOne, playerTwo);
        gameController.displayLabel();

        MainApplication.getMainStage().setTitle("Game started!");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();



    }

    private boolean playerNamesValid() {
        if (playerOneName.getText().isEmpty() || playerTwoName.getText().isEmpty()){
            return false;
        }
        return true;
    }
}

