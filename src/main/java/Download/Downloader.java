package Download;

import com.sun.javafx.tk.Toolkit;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Created by kokoghlanian on 27/04/2017.
 */
public class Downloader extends Task<Void>{
    private String directory;
    private String fileName;
    private String host;
    private File file;
    private Double size;

    public Double getSize() {
        return size;
    }

    public Downloader(String directory, String fileName, String host) {
        this.directory = directory;
        this.fileName = fileName;
        this.host = host;
    }

    @Override
    protected Void call() throws Exception {

        getFile();
        this.updateProgress(file.length(), 1);
        this.updateMessage(this.fileName);
        //this.
        return null;
    }

    public void getFile()
    {
        this.file = new File(directory+"/"+fileName);
        URLConnection website = null;
        String filePath= directory.equals("")?fileName:directory+"/"+fileName;
       try {

           website = new URL(host).openConnection();
           Long contentLength = website.getContentLengthLong();

           this.size = Double.longBitsToDouble(contentLength);

           if(file.length() != contentLength){
            if(file.exists()) {
                website.setRequestProperty("Range", "bytes=" + file.length() + "-");
            }
            FileChannel download = new FileOutputStream(file,file.exists()).getChannel();

            ReadableByteChannel rbc = Channels.newChannel(website.getInputStream());
            download.transferFrom(rbc, file.length(), Long.MAX_VALUE);
            download.close();
           }
    } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args)
    {
        System.out.println("Entrez stop pour arreter le programme (cela ne stopera pas le téléchargement de vos fichier)");
        Scanner sc = new Scanner(System.in);
        String enter = "";
        while (!enter.equalsIgnoreCase("stop")) {
            System.out.println("Enter the directory, the file name and the download link separated by a space");
            enter=sc.nextLine();
            if(!enter.equalsIgnoreCase("stop")) {
                Downloader downloader = new Downloader(enter.split(" ")[0], enter.split(" ")[1], enter.split(" ")[2]);
                Thread t = new Thread(downloader);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
