package hr.tvz.catan.catan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatanPlayersController {

    @FXML
    private TextField player1Name;
    @FXML
    private TextField player2Name;
    @FXML
    private TextField player3Name;
    @FXML
    private TextField player4Name;

    @FXML
    private TextField player1Avatar;
    @FXML
    private TextField player2Avatar;
    @FXML
    private TextField player3Avatar;
    @FXML
    private TextField player4Avatar;

    @FXML
    private AnchorPane mainAnchorPain;
    @FXML
    private AnchorPane player3AnchorPain;
    @FXML
    private AnchorPane player4AnchorPain;
    @FXML
    private Button startButton;
    @FXML
    private Button backButton;
    @FXML
    private AnchorPane avatarPane;

    private List<String> playerNames = new ArrayList<>();
    private List<String> playerAvatars = new ArrayList<>();

    public void setNumberOfPlayers(String numOfPlayers) {
        if (numOfPlayers.equals("2")) {
            mainAnchorPain.setPrefHeight(460);
            player3AnchorPain.setVisible(false);
            player4AnchorPain.setVisible(false);
            backButton.setLayoutY(300);
            avatarPane.setLayoutY(285);
            startButton.setLayoutY(300);
        } else if (numOfPlayers.equals("3")) {
            mainAnchorPain.setPrefHeight(540);
            player4AnchorPain.setVisible(false);
            backButton.setLayoutY(380);
            avatarPane.setLayoutY(365);
            startButton.setLayoutY(380);
        } else if (numOfPlayers.equals("4")) {
            mainAnchorPain.setPrefHeight(620);
            backButton.setLayoutY(460);
            avatarPane.setLayoutY(445);
            startButton.setLayoutY(460);
        } else {
            System.out.println("ERROR! Wrong number of players!");
        }
    }

    @FXML
    public void startGame(ActionEvent event) throws IOException {
        if (player4AnchorPain.isVisible()) {
            if (!player1Name.getText().isEmpty() && !player2Name.getText().isEmpty() &&
                    !player3Name.getText().isEmpty() && !player4Name.getText().isEmpty()) {
                if (checkIfAvatarIsCorrectlySet(player1Avatar.getText()) && checkIfAvatarIsCorrectlySet(player2Avatar.getText()) &&
                        checkIfAvatarIsCorrectlySet(player3Avatar.getText()) && checkIfAvatarIsCorrectlySet(player4Avatar.getText())) {
                    playerNames.add(player1Name.getText());
                    playerNames.add(player2Name.getText());
                    playerNames.add(player3Name.getText());
                    playerNames.add(player4Name.getText());
                    playerAvatars.add(player1Avatar.getText());
                    playerAvatars.add(player2Avatar.getText());
                    playerAvatars.add(player3Avatar.getText());
                    playerAvatars.add(player4Avatar.getText());
                    startGameWithSetPlayers(playerNames, playerAvatars, event);
                }
            }
        } else if (player3AnchorPain.isVisible()) {
            if (!player1Name.getText().isEmpty() && !player2Name.getText().isEmpty() &&
                    !player3Name.getText().isEmpty()) {
                if (checkIfAvatarIsCorrectlySet(player1Avatar.getText()) && checkIfAvatarIsCorrectlySet(player2Avatar.getText()) &&
                        checkIfAvatarIsCorrectlySet(player3Avatar.getText())) {
                    playerNames.add(player1Name.getText());
                    playerNames.add(player2Name.getText());
                    playerNames.add(player3Name.getText());
                    playerAvatars.add(player1Avatar.getText());
                    playerAvatars.add(player2Avatar.getText());
                    playerAvatars.add(player3Avatar.getText());
                    startGameWithSetPlayers(playerNames, playerAvatars, event);
                }
            }
        } else {
            if (!player1Name.getText().isEmpty() && !player2Name.getText().isEmpty()) {
                if (checkIfAvatarIsCorrectlySet(player1Avatar.getText()) && checkIfAvatarIsCorrectlySet(player2Avatar.getText())) {
                    playerNames.add(player1Name.getText());
                    playerNames.add(player2Name.getText());
                    playerAvatars.add(player1Avatar.getText());
                    playerAvatars.add(player2Avatar.getText());
                    startGameWithSetPlayers(playerNames, playerAvatars, event);
                }
            }
        }
    }

    private boolean checkIfAvatarIsCorrectlySet(String playerAvatar) {
        return playerAvatar.equals("1") || playerAvatar.equals("2") || playerAvatar.equals("3") ||
                playerAvatar.equals("4") || playerAvatar.equals("5") || playerAvatar.equals("6");
    }

    private void startGameWithSetPlayers(List<String> playerNames, List<String> playerAvatars, ActionEvent event) throws IOException {
        if (event.getSource() instanceof Button button) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CatanGameScreen.fxml"));
                Parent root = loader.load();
                CatanGameController catanGameController = loader.getController();
                catanGameController.setPlayersOnBoard(playerNames, playerAvatars);
                Scene scene = new Scene(root);
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        if (event.getSource() instanceof Button button) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CatanApplication.class.getResource("CatanNumberOfPlayersScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("There was an error loading a scene! " + e);
            }
        }
    }
}