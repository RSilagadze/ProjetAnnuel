import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Romaaan on 28/04/2017.
 */
public class UrlThread extends Thread implements Runnable{

    private final String urlStr;
    private volatile AtomicBoolean isPause = new AtomicBoolean(false);
    public UrlThread(String url){
        this.urlStr = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            try (InputStream is = connection.getInputStream()) {
                OutputStream os = new FileOutputStream("download.jpg");
                byte[] buff = new byte[2048];
                int bread;
                synchronized (this) {
                    while (true) {
                        if (isPause.get())
                            wait();

                        System.out.println("writing");
                        bread = is.read(buff, 0, buff.length);
                        os.write(buff,0,bread);
                    }
                }

            }
        }catch (Exception  e){
            e.getStackTrace();
        }
    }

    private void writeToFile(){

    }

    public void pause() throws InterruptedException {
        System.out.println("waiting");
        isPause.set(true);
        //this.wait();
    }

    public synchronized void restart(){
        System.out.println("notified");
        isPause.set(false);
        this.notify();
    }
}
