import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InterruptedIOException;

public class ClipBoardListener {

	public static void main(String[] args) throws InterruptedException{
		
			
			
				Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() { 
					   @Override 
					   public void flavorsChanged(FlavorEvent e) {

					      try {
							System.out.println("ClipBoard UPDATED: " + e.getSource() + " " + e.toString()+Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor));
						} catch (HeadlessException e1) {
							// TODO Bloc catch auto-généré
							e1.printStackTrace();
						} catch (UnsupportedFlavorException e1) {
							// TODO Bloc catch auto-généré
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Bloc catch auto-généré
							e1.printStackTrace();
						}
					   } 
					}); 
				
			}
}