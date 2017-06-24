package tools;

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

    public static void launchClipBoardListner() throws InterruptedException{


        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() {
            public void flavorsChanged(FlavorEvent e) {

                try {
                    System.out.println("ClipBoard UPDATED: " + e.getSource() + " " + e.toString()+Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor));
                } catch (HeadlessException e1) {
                    // TODO Bloc catch auto-g?n?r?
                    e1.printStackTrace();
                } catch (UnsupportedFlavorException e1) {
                    // TODO Bloc catch auto-g?n?r?
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Bloc catch auto-g?n?r?
                    e1.printStackTrace();
                }
            }
        });

    }
}
