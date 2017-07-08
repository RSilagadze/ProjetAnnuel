package utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Romaaan on 28/06/2017.
 */

public class ConfigLoader {

    private final Properties properties = new Properties();
    private static final ConfigLoader instance = new ConfigLoader();

    private void readProperties(String file) throws Exception{
        try (FileInputStream fis = new FileInputStream(file)){
            properties.load(fis);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public static void init (String file){
        try {
            instance.readProperties(file);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static Properties getConfigProperties(){
        if (instance.properties.isEmpty())
            init("./webmodule/config.properties");
        return instance.properties;
    }
}
