package utils;

import dbconnector.SqlConnector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Romaaan on 09/07/2017.
 */
public class Utils {

    public static String readFile(String filename) {
        StringBuilder sb = new StringBuilder(2048);
        try (InputStream is = new FileInputStream(filename)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is), 2048);
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return sb.toString();
    }

    public static int executeQuery(String query) {
        int rows = -1;
        try (Connection cn = SqlConnector.getNewConnection()) {
            rows = cn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rows;
    }

}
