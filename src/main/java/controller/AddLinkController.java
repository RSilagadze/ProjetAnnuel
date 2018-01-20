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
import tools.ClipBoard;
import usercontrol.Context;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static tools.Const.DEFAULT_PATH;


/**
 * Created by kokoghlanian on 27/05/2017.
 */
public class AddLinkController implements Initializable {

    public static boolean isLaunched;
    @FXML
    Button okLinkButton;

    @FXML
    TextField fileNameTextField;

    @FXML
    TextField urlTextField;
    private String absoluthPathSaveDirectory;

    @FXML
    protected void handleOkLinkButtonOnClick(ActionEvent event) {

        Stage stage = (Stage) okLinkButton.getScene().getWindow();
        if (absoluthPathSaveDirectory == null)
            this.absoluthPathSaveDirectory = DEFAULT_PATH;
        Downloader downloader = new Downloader(
                absoluthPathSaveDirectory,
                fileNameTextField.getText(),
                urlTextField.getText(),
                (finished_download) -> LinkMetier.deleteLink(finished_download.getHost())
        );
        LinkMetier.insertLink(urlTextField.getText(), Context.getCurrentUser().getId(), new Date(), fileNameTextField.getText());
        ControllerMediator.getInstance().executeDownload(downloader);
        isLaunched = false;
        stage.close();
    }

    @FXML
    protected void handleFileChooserButtonOnClick(ActionEvent event) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose download directory");
        File file = directoryChooser.showDialog(new Stage());

        if (file != null && file.exists()) {
            absoluthPathSaveDirectory = file.getAbsolutePath();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerAddLinkController(this);
        isLaunched = true;
        if (!ClipBoard.content.equals("")) {
            this.urlTextField.setText(ClipBoard.content);
        }
        ClipBoard.content = "";
    }
}
