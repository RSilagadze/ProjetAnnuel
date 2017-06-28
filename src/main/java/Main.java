import dbconnector.SqlConnector;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import metier.UserMetier;
import usercontrol.Context;
import utils.ConfigLoader;
import utils.Queries;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
        primaryStage.setTitle("Downloader");
        primaryStage.setScene(new Scene(root, 1366, 800));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException {
        SqlConnector.init();
        launch(args);
    }

}