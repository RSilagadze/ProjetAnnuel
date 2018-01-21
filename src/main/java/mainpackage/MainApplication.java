package mainpackage;

import crypter.asymetric.RSACrypter;
import crypter.asymetric.RSAKeyManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApplication extends Application {

    public static void main(String[] args) {
        RSAKeyManager.generateKeys();

        RSACrypter.decrpytRSAFile(RSAKeyManager.getUserPrivateKeyFilePath(), "rsa_decrypted", "rsa_crypted");

        //launch(args);
        //sendMessage("test","test","sirac.nicolas@gmail.com");
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
