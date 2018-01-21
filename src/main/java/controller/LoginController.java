package controller;


import com.google.common.hash.Hashing;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.UserMetier;
import usercontrol.Context;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Created by kokoghlanian on 28/06/2017.
 */
public class LoginController implements Initializable {
    private static int connectionTry = 0;
    private static boolean threadRun = false;
    @FXML
    TextField loginTextField;
    @FXML
    TextField passTextField;

    @FXML
    protected void handleConnexionButtonOnClick(ActionEvent event) {
        if (connectionTry < 3) {
            String password = passTextField.getText();
            System.out.println(password);
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                password = Hashing.sha256()
                        .hashString(password, StandardCharsets.UTF_8)
                        .toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            System.out.println(password);
            User user = UserMetier.getUser(loginTextField.getText(), password);
            if (!user.isEmpty() && user != null) {
                try {

                    Context.setCurrentUser(user);
                    AuthentController.user = user;
                    AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/doubleAuthent.fxml"));

                    AuthentController linkStage = new AuthentController();

                    linkStage.setTitle("Authentification");
                    linkStage.initStyle(StageStyle.DECORATED);
                    linkStage.setScene(new Scene(root, 1366, 800));
                    linkStage.show();
                } catch (IOException e) {
                    System.out.println("Impossible d'afficher la main page");
                    e.printStackTrace();
                } finally {
                    Stage stage = (Stage) this.loginTextField.getScene().getWindow();
                    stage.close();
                }
            } else {
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.DECORATED);
                Scene scene = new Scene(new Group(new Text(20, 20, "Login Error!")), 150, 50);
                connectionTry++;
                dialog.setScene(scene);
                dialog.show();

            }
        } else {

            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.DECORATED);
            Scene scene = new Scene(new Group(new Text(20, 20, "To much bad attempt, please wait!")), 350, 50);

            dialog.setScene(scene);
            dialog.show();
            if (!LoginController.threadRun) {
                TimerThread tt = new TimerThread();
                Thread t = new Thread(tt);
                threadRun = true;
                t.start();
            }

        }

    }

    @FXML
    protected void handleAnonymousButtonOnClick(ActionEvent event) {

        try {
            AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/mainWindow.fxml"));
            Stage linkStage = new Stage();
            linkStage.setTitle("Downloader");
            linkStage.initStyle(StageStyle.DECORATED);
            linkStage.setScene(new Scene(root, 1366, 800));
            linkStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
            linkStage.show();
        } catch (IOException e) {
            System.out.println("Impossible d'afficher la main page");
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) this.loginTextField.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    protected void handleRegisterButtonOnClick(ActionEvent event) {
        tools.CreateUserSite.displayURL();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public class TimerThread implements Runnable {

        @Override
        public void run() {
            long time = System.currentTimeMillis();
            long timer = 0;
            while (timer < 3000) {

                timer = System.currentTimeMillis() - time;

            }

            System.out.println("in");
            LoginController.connectionTry = 0;
            LoginController.threadRun = false;
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
