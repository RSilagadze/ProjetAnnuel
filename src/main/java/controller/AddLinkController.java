package controller;

import Download.Downloader;

import entities.Link;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import metier.LinkMetier;
import metier.UserMetier;
import tools.Const;
import usercontrol.Context;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static tools.Const.*;


/**
 * Created by kokoghlanian on 27/05/2017.
 */
public class AddLinkController implements Initializable{

    private String absoluthPathSaveDirectory;

    @FXML
    Button okLinkButton;

    @FXML
    TextField fileNameTextField;

    @FXML
    TextField urlTextField;


    @FXML
    protected  void handleOkLinkButtonOnClick(ActionEvent event){

        Stage stage = (Stage) okLinkButton.getScene().getWindow();
        if(absoluthPathSaveDirectory==null)
            this.absoluthPathSaveDirectory= DEFAULT_PATH;
        Downloader downloader = new Downloader(
                absoluthPathSaveDirectory,
                fileNameTextField.getText(),
                urlTextField.getText(),
                (finished_download) -> {LinkMetier.deleteLink(finished_download.getHost());}
        );
        LinkMetier.insertLink(urlTextField.getText(), Context.getCurrentUser().getId(), new Date(), fileNameTextField.getText());
        ControllerMediator.getInstance().executeDownload(downloader);
        stage.close();
    }

    @FXML
    protected  void handleFileChooserButtonOnClick(ActionEvent event) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose download directory");
        File file = directoryChooser.showDialog(new Stage());
        absoluthPathSaveDirectory  = file.getAbsolutePath();
    }

    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerAddLinkController(this);
    }
}
