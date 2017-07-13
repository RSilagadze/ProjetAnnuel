package mainpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application {

    @Override 
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/loginWindow.fxml"));
        primaryStage.setTitle("Downloader");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);
    }

}
