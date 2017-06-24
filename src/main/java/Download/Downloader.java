package Download;

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
    private String directory;
    private String fileName;
    private String host;
    public File file;
    private Double size;
    public URLConnection website;
    private static final int BUFFER_SIZE = 4096;

    public Double getSize() {
        return size;
    }

    public Downloader(String directory, String fileName, String host) {
        this.directory = directory;
        this.fileName = fileName;
        this.host = host;
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

        this.updateMessage(this.fileName);
        while(!Thread.currentThread().isInterrupted()){
            if(!suspended){
                try {
                    downloadFile(host,directory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    while(suspended){
                        synchronized(this){
                            this.wait();
                        }
                    }
                }
                catch (InterruptedException e) {
                }
            }
        }
        return null;
    }

    private void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
            if(contentLength==-1&&httpConn.getHeaderField("Content-Disposition")!=null){
                this.size=Double.valueOf(httpConn.getHeaderField("Content-Length"));
                contentLength = this.size.intValue();
            }
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
            while ((bytesRead = inputStream.read(buffer)) != -1 && !suspended) {
                totalBytes += bytesRead;
                this.updateProgress(totalBytes,contentLength);
                System.out.println(totalBytes);
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

}
