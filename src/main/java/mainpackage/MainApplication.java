package mainpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/loginWindow.fxml"));
        primaryStage.setTitle("Downloader");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        primaryStage.show();

    }

}
