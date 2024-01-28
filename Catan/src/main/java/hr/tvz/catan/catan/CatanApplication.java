package hr.tvz.catan.catan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CatanApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CatanApplication.class.getResource("CatanStartScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 310, 320);
        stage.setTitle("Catan");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/logo.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}