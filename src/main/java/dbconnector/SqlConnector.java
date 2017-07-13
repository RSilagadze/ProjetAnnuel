package dbconnector;

import utils.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class SqlConnector {

    private static final String cnString = ConfigLoader.getConfigProperties().getProperty("connectionString");

    public static Connection getNewConnection()  {
        Connection cn = null;
        try{
            cn = DriverManager.getConnection(cnString);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return cn;
    }

}
