package hr.tvz.catan.catan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CatanNumberOfPlayersController {

    @FXML
    public void goBack(ActionEvent event) {
        CatanStartController catanStartController = new CatanStartController();
        catanStartController.goBack(event);
    }

    @FXML
    public void chooseNumberOfPlayers(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CatanPlayersScreen.fxml"));
                Parent root = loader.load();
                CatanPlayersController catanPlayersController = loader.getController();
                catanPlayersController.setNumberOfPlayers(button.getText());
                Scene scene = new Scene(root);
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                System.out.println("There was an error loading a scene! " + e);;
            }
        }
    }
}