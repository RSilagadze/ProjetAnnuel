package tools;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
     * Created by Sirac on 24/06/2017.
 */
public class ClipBoard {

    public static String content="";
    public static void launchClipBoardListner() throws InterruptedException{


        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() {
            public void flavorsChanged(FlavorEvent e) {


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() { try {
                            content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor).toString();

                            AnchorPane root  = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/addLinkWindow.fxml"));
                            Stage linkStage = new Stage();
                            linkStage.setTitle("Add Link");
                            linkStage.initStyle(StageStyle.DECORATED);
                            linkStage.setScene(new Scene(root, 500, 100));
                            linkStage.show();
                        }
                catch (HeadlessException e1) {
                            // TODO Bloc catch auto-g?n?r?
                            e1.printStackTrace();
                        } catch (UnsupportedFlavorException e1) {
                            // TODO Bloc catch auto-g?n?r?
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            // TODO Bloc catch auto-g?n?r?
                            e1.printStackTrace();
                        }
                    }});



            }
        });

    }
}
