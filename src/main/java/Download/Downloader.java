package Download;

import entities.Link;
import interfaces.IPostback;
import javafx.concurrent.Task;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by kokoghlanian on 27/04/2017.
 */
public class Downloader extends Task<Void> {

    private volatile boolean suspended = false;
    private static final int BUFFER_SIZE = 4096;

    private String directory;
    private String fileName;
    private String host;
    protected Double size;
    boolean finish=false;
    public URLConnection website;

    private final IPostback<Downloader> ipostback;

    public Double getSize() {
        return size;
    }

    public Downloader(String directory, String filename, String host, IPostback<Downloader> onpostback) {
        this.directory = directory;
        this.fileName = filename;
        this.host = host;
        this.ipostback = onpostback;
    }

    public void suspend(){
        suspended = true;
    }

    public void resume(){
        suspended = false;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public Void call() {
        try {
            updateMessage("Init");
            updateTitle(fileName);
            downloadFile(host,directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        //httpConn.setRequestProperty("Range", "bytes=" + new File(saveDir).length() + "-");
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();

            Double contentLength = Double.valueOf(httpConn.getContentLength());

            if(contentLength==-1 && httpConn.getHeaderField("Content-Disposition")!=null){
                contentLength = Double.valueOf(httpConn.getHeaderField("Content-Length"));
            }

            this.size = Double.valueOf(contentLength);
            for(String s : httpConn.getHeaderFields().keySet()){
                System.out.println("Fields : "+s+"   "+httpConn.getHeaderFields().get(s));
            }

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
            if(fileName.equals("")){
                fileName="default";
            }
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + this.fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            int totalBytes = 0;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((bytesRead = inputStream.read(buffer)) != -1){
                totalBytes += bytesRead;
                this.updateProgress(totalBytes,contentLength);
                try {
                    while(suspended){
                        synchronized(this){
                            System.out.println("OnPause");
                            this.updateMessage("Pause");
                            this.wait();
                        }
                    }
                }
                catch (InterruptedException e) {
                }
                this.updateMessage("Play");
                System.out.println(totalBytes);
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            this.updateMessage("Done");
            System.out.println("File downloaded");
            ipostback.onPostedBack(this);
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
        finish=true;
    }

    public String getHost() {
        return host;
    }

}
