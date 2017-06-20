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

    public Void call() {
        try {

            this.updateMessage(this.fileName);
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
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
            this.size = Double.valueOf(contentLength);
            if(contentLength==-1&&httpConn.getHeaderField("Content-Disposition")!=null){
                size=Double.valueOf(httpConn.getHeaderField("Content-Length"));
                contentLength = size.intValue();
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
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
                System.out.println(totalBytes);
                this.updateProgress(totalBytes,contentLength);
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


    public static void main(String[] args)
    {
     /*  System.out.println("Entrez stop pour arreter le programme (cela ne stopera pas le téléchargement de vos fichier)");
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
        }*/
     Downloader d = new Downloader("C:\\Users\\Nicolas\\Desktop","","http://www20.uptobox.com/d/wuys64fkhsr76xkqbcppe3tffzsrktt5tmvjgcdezyjmtnbyn3jjojyl6mztumjdzpiank6e5tuldza/Beauty.and.the.Beast.2017.FRENCH.BRRip.XviD-NEWCiNE-WwW.Zone-Telechargement.Ws.avi");
     Thread t= new Thread(d);
     t.start();

    }


}
