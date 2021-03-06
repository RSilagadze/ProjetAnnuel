package dao;

import java.util.List;

/**
 * Created by Romaaan on 27/06/2017.
 */
public interface IDAO<T> {
    T get(String query, Object... args);

    List<T> getList(String query, Object... args);

    int updateOrDelete(String query, Object... args);

    int insert(String query, Object... args);
}
