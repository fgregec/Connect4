package hr.algebra.connect4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenController {
    Stage mainStage = MainApplication.getMainStage();
    Parent root;

    public void changeToStartScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Start game!");
        mainStage.setScene(scene);
    }

    public void changeToChatScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("chatScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Chat");
        mainStage.setScene(scene);
    }

    public void changeToLeaderboardScreen() throws IOException{



    }

    public void changeToHistoryScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("historyScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Game History");
        mainStage.setScene(scene);
    }
}
