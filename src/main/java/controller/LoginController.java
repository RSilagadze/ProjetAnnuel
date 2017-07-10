package controller;


import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.UserMetier;
import usercontrol.Context;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kokoghlanian on 28/06/2017.
 */
public class LoginController implements Initializable {

    @FXML
    TextField loginTextField;

    @FXML
    TextField passTextField;

    @FXML
    protected void handleConnexionButtonOnClick(ActionEvent event) {

        User user = UserMetier.getUser(loginTextField.getText(), passTextField.getText());
        if (!user.isEmpty()) {
            try {
                Context.setCurrentUser(user);
                AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/mainWindow.fxml"));
                Stage linkStage = new Stage();
                linkStage.setTitle("Downloader");
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

            dialog.setScene(scene);
            dialog.show();
        }
    }

    @FXML
    protected void handleAnonymousButtonOnClick(ActionEvent event) {

        User user = UserMetier.getUser(loginTextField.getText(), passTextField.getText());

        try {
            AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/mainWindow.fxml"));
            Stage linkStage = new Stage();
            linkStage.setTitle("Downloader");
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

    }@FXML
    protected void handleRegisterButtonOnClick(ActionEvent event){
        tools.CreateUserSite.displayURL();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
