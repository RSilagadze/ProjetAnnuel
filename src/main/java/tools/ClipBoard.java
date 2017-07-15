package tools;

import controller.AddLinkController;
import controller.ControllerMediator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by Sirac on 24/06/2017.
 */
public class ClipBoard {

    public static String content = "";

    public static void launchClipBoardListner() throws InterruptedException {


        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> Platform.runLater(() -> {
            try {
                content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor).toString();

                if( ControllerMediator.getInstance().getAddLinkController() == null || !AddLinkController.isLaunched) {
                    AnchorPane root = FXMLLoader.load(mainpackage.MainApplication.class.getResource("/addLinkWindow.fxml"));
                    Stage linkStage = new Stage();
                    linkStage.setTitle("Add Link");
                    linkStage.initStyle(StageStyle.DECORATED);
                    linkStage.setScene(new Scene(root, 500, 100));
                    linkStage.show();
                }
            } catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
                // TODO Bloc catch auto-g?n?r?
                e1.printStackTrace();
            }
        }));

    }
}
