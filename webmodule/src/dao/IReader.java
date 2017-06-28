package dao;

import java.sql.ResultSet;

/**
 * Created by Romaaan on 27/06/2017.
 */
public interface IReader<T> {
    T readResult(ResultSet rs);
}
