package controller;

import Download.Downloader;
import adapter.DownloaderAdapter;
import crypter.asymetric.RSACrypter;
import crypter.asymetric.RSAKeyManager;
import crypter.symetric.SymetricKeyManager;
import entities.Link;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.LinkMetier;
import tools.ClipBoard;
import usercontrol.Context;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static crypter.symetric.CBCCrypter.decryptCBCFile;
import static crypter.symetric.CBCCrypter.encryptCBCFile;
import static crypter.symetric.SymetricKeyManager.readKey;

public class MainWindowController implements Initializable {

    private final List<Future<?>> future = new ArrayList<>();
    @FXML
    TableView<Downloader> downloadTab;
    @FXML
    TableColumn<Downloader, String> fileNameCol;
    @FXML
    TableColumn<Downloader, Long> sizeCol;
    @FXML
    TableColumn<Downloader, Double> progressCol;
    @FXML
    TableColumn<Downloader, String> statusImageCol;
    @FXML
    MenuItem addLinkMenuItem;
    @FXML
    MenuItem encryptCBCItem;
    @FXML
    MenuItem decryptCBCItem;
    @FXML
    MenuItem cryptRSAItem;
    @FXML
    MenuItem decryptRSAItem;
    @FXML
    ToggleButton pauseButton;
    @FXML
    ToggleButton playToggleButton;
    @FXML
    ToggleButton deleteButton;
    private Downloader selectedTask;

    @FXML
    protected void handleEncryptCBCItemButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null && file.exists()) {
            try {
                File keyFile = new File(SymetricKeyManager.getUserKeyFilePath());

                if (!keyFile.exists()) {
                    SymetricKeyManager.saveKey();
                }

                System.out.println(file.getAbsolutePath());
                encryptCBCFile(file.getAbsolutePath(), readKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleDecryptCBCItemButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null && file.exists()) {
            try {
                File keyFile = new File(SymetricKeyManager.getUserKeyFilePath());

                if (!keyFile.exists()) {
                    SymetricKeyManager.saveKey();
                }

                System.out.println(file.getAbsolutePath());
                decryptCBCFile(file.getAbsolutePath(), readKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleEncryptRSAItemButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null && file.exists()) {
            try {
                File publicKeyFile = new File(RSAKeyManager.getUserPublicKeyFilePath());

                if (!publicKeyFile.exists()) {
                    RSAKeyManager.saveKeys();
                }

                System.out.println(file.getAbsolutePath());
                RSACrypter.encryptRSAFile(RSAKeyManager.getUserPublicKeyFilePath(), file.getAbsolutePath(), file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleDecryptRSAItemButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null && file.exists()) {
            try {
                File privateKeyFile = new File(RSAKeyManager.getUserPrivateKeyFilePath());

                if (!privateKeyFile.exists()) {
                    RSAKeyManager.saveKeys();
                }

                System.out.println(file.getAbsolutePath());
                RSACrypter.decrpytRSAFile(RSAKeyManager.getUserPrivateKeyFilePath(), file.getAbsolutePath(), file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleAddLinkMenuItemOnClick(ActionEvent event) {

        try {

            AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/addLinkWindow.fxml"));
            Stage linkStage = new Stage();
            AddLinkController.isLaunched = true;
            linkStage.setTitle("Add Link");
            linkStage.initStyle(StageStyle.DECORATED);
            linkStage.setScene(new Scene(root, 500, 100));
            linkStage.show();

        } catch (IOException e) {
            System.out.println("Impossible d'afficher la linkPage");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDeleteItemButtonOnClick(ActionEvent event) {
        downloadTab.getItems().remove(selectedTask);
    }

    @FXML
    protected void handlePauseItemButtonOnClick(ActionEvent event) {
        this.selectedTask.suspend();
    }

    @FXML
    protected void handleResumeItemButtonOnClick(ActionEvent event) {
        this.selectedTask.resume();
    }

    public void launchDownload(Downloader downloader) {

        downloadTab.getItems().add(downloader);

        ExecutorService executor = getExecutorThreadPool();

        for (Downloader task : downloadTab.getItems()) {
            System.out.println("Execute Task");
            future.add(executor.submit(task));
            executor.execute(task);
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {

                    if (downloader.isDone() && !downloader.finish) {
                        LinkMetier.deleteLink(downloader.getHost());
                        downloadTab.getItems().remove(downloader);
                        Stage dialog = new Stage();
                        dialog.initStyle(StageStyle.DECORATED);
                        Scene scene = new Scene(new Group(new Text(20, 20, "URL : " + downloader.getHost() + "\n is invalid")), 150, 50);

                        dialog.setScene(scene);
                        dialog.show();
                    }
                }

            }
        });

    }

    private ExecutorService getExecutorThreadPool() {
        if (downloadTab.getItems() == null)
            return null;
        return Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    private void launchDownloadList(List<Link> linkListToDownload) {

        int size = linkListToDownload.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (linkListToDownload.get(i).getUrl().equals(linkListToDownload.get(i).getUrl()) || linkListToDownload.get(i).getName().equals(linkListToDownload.get(i).getName())) {
                    linkListToDownload.remove(j);
                    size--;
                }
            }
        }

        for (Link aLinkListToDownload : linkListToDownload) {
            launchDownload(DownloaderAdapter.linkToDownloader(aLinkListToDownload));
        }


    }

    public void initialize(URL location, ResourceBundle resources) {
        ControllerMediator.getInstance().registerMainWindowController(this);

        ClipBoard.launchClipBoardListner();
        pauseButton.disableProperty().bind(Bindings.isEmpty(downloadTab.getSelectionModel().getSelectedItems()));

        //downloadTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        downloadTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedTask = downloadTab.getSelectionModel().getSelectedItem());

        statusImageCol.setCellValueFactory(new PropertyValueFactory<>(
                "message"));

        fileNameCol.setCellValueFactory(new PropertyValueFactory<>(
                "title"));

        //bind sur les getter de la classe downloader.
        sizeCol.setCellValueFactory(new PropertyValueFactory<>(
                "value"));

        progressCol.setCellValueFactory(new PropertyValueFactory<>(
                "progress"));
        progressCol
                .setCellFactory(ProgressBarTableCell.forTableColumn());

        downloadTab.getColumns().setAll(statusImageCol, fileNameCol, sizeCol, progressCol);

        List<Link> links = LinkMetier.getLinkListByUserId(Context.getCurrentUser().getId());
        launchDownloadList(links);

    }
}