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
public class Downloader {
    public static void getFile(String host,String fileName)
    {
        URLConnection website = null;
       try {
            File file = new File(fileName);
            System.out.println(file.length()+" 0");
           website = new URL(host).openConnection();
            if(file.exists())
                website.setRequestProperty("Range", "bytes=" + file.length() + "-");
            FileChannel download = new FileOutputStream(file,file.exists()).getChannel();


            ReadableByteChannel rbc = Channels.newChannel(website.getInputStream());


           download.transferFrom(rbc, file.length(), Long.MAX_VALUE);
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
        getFile("http://www7.uptobox.com/d/vay62japhsr76xkqugcmobreou4nigqeinigtpzdsl6ajlheroriiskycj3zpwjzrge3kont4tcqrjqeve/Lion.2016.FRENCH.BRRip.XviD-NEWCiNE-WwW.Zone-Telechargement.Ws.avi","screen.jpg");
    }
}
