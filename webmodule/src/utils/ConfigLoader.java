package utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class ConfigLoader {

    private final Properties properties = new Properties();
    private static final ConfigLoader instance = new ConfigLoader();

    private ConfigLoader(){
        readProperties("webmodule/config.properties");
    }

    private void readProperties(String file){
        try (FileInputStream fis = new FileInputStream(file)){
            properties.load(fis);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public static Properties getConfigProperties(){
        return instance.properties;
    }

}
