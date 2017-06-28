package dbconnector;

import utils.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class SqlConnector {

    private static String cnString = null;

    public static void init(){
        cnString = ConfigLoader.getConfigProperties().getProperty("connectionString");
    }

    public static Connection getNewConnection()  {
        Connection cn = null;
        try{
            if (cnString == null)
                init();
            cn = DriverManager.getConnection(cnString);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return cn;
    }

}
