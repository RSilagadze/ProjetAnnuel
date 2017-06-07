package controller;

import Download.Downloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MainWindowController implements Initializable{

    @FXML
    TableView<Downloader> downloadTab;

    @FXML
    TableColumn<Downloader,String> fileNameCol;

    @FXML
    TableColumn<Downloader,Double> sizeCol;

    @FXML
    TableColumn<Downloader,Double> progressCol;

    @FXML
    MenuItem addLinkMenuItem;

    @FXML
    protected  void handleAddLinkMenuItemOnClick(ActionEvent event){

        try {

            AnchorPane root  = FXMLLoader.load(getClass().getResource("../addLinkWindow.fxml"));
            Stage linkStage = new Stage();
            linkStage.setTitle("Add Link");
            linkStage.initStyle(StageStyle.DECORATED);
            linkStage.setScene(new Scene(root, 500, 100));
            linkStage.show();

        } catch (IOException e) {
            System.out.println("Impossible d'afficher la linkPage");
            e.printStackTrace();
        }
    }

    public void launchDownload(Downloader downloader) {


        downloadTab.getItems().add(downloader);

        ExecutorService executor = Executors.newFixedThreadPool(downloadTab.getItems().size(), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        for (Downloader task : downloadTab.getItems()) {
            executor.execute(task);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        ControllerMediator.getInstance().registerMainWindowController(this);


        downloadTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //bind sur les getter de la classe downloader.
        sizeCol.setCellValueFactory(new PropertyValueFactory<Downloader, Double>(
                "size"));

        fileNameCol.setCellValueFactory(new PropertyValueFactory<Downloader, String>(
                "message"));

        progressCol.setCellValueFactory(new PropertyValueFactory<Downloader, Double>(
                "progress"));
        progressCol
                .setCellFactory(ProgressBarTableCell.<Downloader> forTableColumn());
        downloadTab.getColumns().setAll(fileNameCol,sizeCol,progressCol);
    }
}