import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by kokoghlanian on 27/04/2017.
 */
public class Downloader implements Runnable{
    String directory;
    String fileName;
    String host;

    public Downloader(String directory, String fileName, String host) {
        this.directory = directory;
        this.fileName = fileName;
        this.host = host;
    }

    public void getFile()
    {File file = new File(directory+"/"+fileName);
        System.out.println("in");
        URLConnection website = null;
        String filePath= directory.equals("")?fileName:directory+"/"+fileName;
        System.out.println("out");
       try {

            //System.out.println(file.length()+" 0");
           website = new URL(host).openConnection();
            if(file.exists()) {
                System.out.println("in");
                website.setRequestProperty("Range", "bytes=" + file.length() + "-");
            }
           System.out.println("in");
            FileChannel download = new FileOutputStream(file,file.exists()).getChannel();

            ReadableByteChannel rbc = Channels.newChannel(website.getInputStream());

           System.out.println(website.getContentLengthLong());

          // download.transferFrom(rbc, file.length(), Long.MAX_VALUE);
    } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run() {
        getFile();
    }
    public static void main(String[] args)
    {
        Downloader downloader= new Downloader("C:/Users/Nicolas/Desktop","video.avi","http://www18.uptobox.com/d/wmytzdl4hwr76xkqrkp7e3ryes32pnwnicpvt2ihn7y76tt5kowitwbynrti4qoqlfa6njpvwaymddy/Logan.2017.FRENCH.BRRip.XviD.AC3-NEWCiNE-WwW.Zone-Telechargement.Ws.avi");
        Thread t = new Thread(downloader);
        t.start();
    }


}
