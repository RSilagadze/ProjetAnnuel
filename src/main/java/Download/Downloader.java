package Download;

import interfaces.IPostback;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;


public class Downloader extends Task<Long> {

    private volatile boolean suspended = false;
    private static final int BUFFER_SIZE = 4096;

    private final String directory;
    private final String fileName;
    private final String host;
    protected Long size;
    public boolean finish = false;
    public URLConnection website;

    private final IPostback<Downloader> ipostback;

    public Long getSize() {
        return size;
    }

    public Downloader(String directory, String filename, String host, IPostback<Downloader> onpostback) {
        this.directory = directory;
        this.fileName = filename;
        this.host = host;
        this.ipostback = onpostback;
    }

    public void suspend() {
        suspended = true;
    }

    public void resume() {
        suspended = false;
        synchronized (this) {
            this.notifyAll();
        }
    }


    public Long call() throws InterruptedException, ExecutionException {
        try {
            updateMessage("Init");
            updateTitle(fileName);
            downloadFile(host, directory);
        } catch (IOException e) {
            synchronized (this) {
                this.notifyAll();
            }
            throw new ExecutionException(e);
        }
        return size;
    }

    private void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        System.out.println(saveDir);
        File f = new File(saveDir + "/" + fileName);
        if (f.exists() && httpConn.getHeaderField(" If-Range") != null)
            httpConn.setRequestProperty("Range", "bytes=" + f.length() + "-");
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();

            Long contentLength = (long) httpConn.getContentLength();

            if (contentLength == -1 && httpConn.getHeaderField("Content-Length") != null) {
                contentLength = Long.valueOf(httpConn.getHeaderField("Content-Length"));
            }

            this.size = contentLength;
            this.updateValue(size);
            for (String s : httpConn.getHeaderFields().keySet()) {
                System.out.println("Fields : " + s + "   " + httpConn.getHeaderFields().get(s));
            }


            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + this.fileName;

            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            int totalBytes = 0;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
                this.updateProgress(totalBytes, contentLength);
                try {
                    while (suspended) {
                        synchronized (this) {
                            System.out.println("OnPause");
                            this.updateMessage("Pause");
                            this.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
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
        finish = true;
    }

    public String getHost() {
        return host;
    }

}
