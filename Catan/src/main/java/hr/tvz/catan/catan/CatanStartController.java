package hr.tvz.catan.catan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CatanStartController {

    @FXML
    public void startGame(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CatanNumberOfPlayersScreen.fxml"));
                Scene scene = new Scene(loader.load());
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

    @FXML
    public void aboutGame(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CatanApplication.class.getResource("CatanAboutScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                System.out.println("There was an error loading a scene! " + e);
            }

        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CatanApplication.class.getResource("CatanStartScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("There was an error loading a scene! " + e);
            }
        }
    }

    @FXML
    public void exitGame(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }
}