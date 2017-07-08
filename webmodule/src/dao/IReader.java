package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Romaaan on 27/06/2017.
 */
public interface IReader<T> {
    T readResult(ResultSet rs) throws SQLException;
}
