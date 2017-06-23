import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InterruptedIOException;

public class ClipBoardListener extends Thread implements ClipboardOwner{
Clipboard sysClip ;  
boolean wait=true;

String  clipboardText="";
String previous="";

    @Override
  public void run() {
    	System.out.println(this.getId());
    	StringSelection stringSelection = new StringSelection("");
    		sysClip= Toolkit.getDefaultToolkit().getSystemClipboard();
    		sysClip.setContents(stringSelection, this);
        //sysClip.setContents(stringSelection, this);
    while(true){
    	
    	clipboardText=process_clipboard();
    	if(!clipboardText.equals("")){
    		while(wait){
    			System.out.println("inin");
    			try {
    				this.join(100);
					this.sleep(150);
				} catch (InterruptedException e) {
					// TODO Bloc catch auto-généré
					e.printStackTrace();
				}
    		}
    		wait=true;
    		previous=clipboardText;
    		clipboardText="";
    	}
    }
  }  

    
    @Override
  public void lostOwnership(Clipboard c, Transferable t) {  

  


}  

  void TakeOwnership(Transferable t) {  
    sysClip.setContents(t, this);  
  }  

public String process_clipboard() { 
	String result = "";
    
    //odd: the Object param of getContents is not currently used
    Transferable contents = sysClip.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor);
    if (hasTransferableText) {
        try {
			result = (String)contents.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloc catch auto-généré
			e.printStackTrace();
		}
      
      
    }
   System.out.println(result+" : "+this.previous);
    return result.equals(this.previous)?"":result;
}
	public static void main(String[] args) throws InterruptedException{
		
			ClipBoardListener c= new ClipBoardListener();
			
			c.start();
			while(true){
			while(c.clipboardText.equals("")){
			}
				System.out.println(c.clipboardText);
				c.wait=false;
				
				//System.out.println("out");
				
				/*c=new ClipBoardListener();
				c.start();
				while(c.isAlive()){
				}
					System.out.println(c.clipboardText);*/
	}}
}