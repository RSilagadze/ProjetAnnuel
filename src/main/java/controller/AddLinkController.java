package controller;

import Download.Downloader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import metier.LinkMetier;
import tools.Const;

import java.io.File;
import java.net.URL;
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
