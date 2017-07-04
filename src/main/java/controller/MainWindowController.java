package controller;

import Download.Downloader;
import com.sun.javaws.LaunchDownload;
import entities.Link;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.LinkMetier;
import tools.ClipBoard;
import adapter.DownloaderAdapter;
import usercontrol.Context;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    TableColumn<Downloader,String> statusImageCol;

    @FXML
    MenuItem addLinkMenuItem;

    @FXML
    ToggleButton pauseButton;

    @FXML
    ToggleButton playToggleButton;

    @FXML
    ToggleButton deleteButton;

    private Downloader selectedTask;
    private List<Future<?>> future = new ArrayList<Future<?>>();

    @FXML
    protected void handleAddLinkMenuItemOnClick(ActionEvent event){

        try {

            AnchorPane root  = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/addLinkWindow.fxml"));
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

    @FXML
    protected void handleDeleteItemButtonOnClick(ActionEvent event){
        downloadTab.getItems().remove(selectedTask);
    }

    @FXML
    protected void handlePauseItemButtonOnClick(ActionEvent event){
            this.selectedTask.suspend();
    }

    @FXML
    protected void handleResumeItemButtonOnClick(ActionEvent event){
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
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!downloader.isDone());
                if(!downloader.finish)
                    downloadTab.getItems().remove(downloader);
            }
        });
        t.start();
    }

    public ExecutorService getExecutorThreadPool(){
        if(downloadTab.getItems() == null)
            return null;
        return Executors.newFixedThreadPool(10, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }

    public void launchDownloadList(List<Link> linkListToDownload){
         
             int size=linkListToDownload.size();
            for(int i=0;i<size;i++){
              for(int j=i+1;j<size;j++){
                  if(linkListToDownload.get(i).getUrl().equals(linkListToDownload.get(i).getUrl())){
                      linkListToDownload.remove(j);
                      size--;
                  }
              }
            }

        for(int i= 0; i< linkListToDownload.size(); i++){
           launchDownload(DownloaderAdapter.linkToDownloader(linkListToDownload.get(i)));
        }


    }

    public void initialize(URL location, ResourceBundle resources){
        ControllerMediator.getInstance().registerMainWindowController(this);

        try {
            ClipBoard.launchClipBoardListner();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseButton.disableProperty().bind(Bindings.isEmpty(downloadTab.getSelectionModel().getSelectedItems()));

        //downloadTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        downloadTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){

            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedTask = downloadTab.getSelectionModel().getSelectedItem();
            }
        });

        statusImageCol.setCellValueFactory(new PropertyValueFactory<Downloader, String>(
                "message"));

        fileNameCol.setCellValueFactory(new PropertyValueFactory<Downloader, String>(
                "title"));

        //bind sur les getter de la classe downloader.
        sizeCol.setCellValueFactory(new PropertyValueFactory<Downloader, Double>(
                "size"));

        progressCol.setCellValueFactory(new PropertyValueFactory<Downloader, Double>(
                "progress"));
        progressCol
                .setCellFactory(ProgressBarTableCell.<Downloader> forTableColumn());

        downloadTab.getColumns().setAll(statusImageCol,fileNameCol,sizeCol,progressCol);

        List<Link> links = LinkMetier.getLinkListByUserId(Context.getCurrentUser().getId());
        launchDownloadList(links);

    }
}