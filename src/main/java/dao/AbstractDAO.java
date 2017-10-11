package dao;

import dbconnector.SqlConnector;
import entities.DefaultEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class AbstractDAO<T extends DefaultEntity> implements IDAO<T>{

    protected IReader<T> reader = (r) -> null;

    @Override
    public T get(String query, Object... args) {
        T entity = null;
        try (Connection cn = SqlConnector.getNewConnection()){
            PreparedStatement st = cn.prepareStatement(query);
            putParameters(st, args);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                entity = reader.readResult(rs);
            }
        } catch (SQLException e) {
          //  System.err.println(e.getMessage());

        }
        finally {
            return entity;
        }

    }

    @Override
    public List<T> getList(String query, Object... args) {
        List<T> lst = new ArrayList<>(64);
        try (Connection cn = SqlConnector.getNewConnection()){
            PreparedStatement st = cn.prepareStatement(query);
            putParameters(st, args);
            ResultSet rs = st.executeQuery();
            T ent;
            while(rs.next()){
                ent = reader.readResult(rs);
                lst.add(ent);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lst;
    }

    @Override
    public int updateOrDelete(String query, Object... args) {
        int rows = -1;
        try (Connection cn = SqlConnector.getNewConnection()){
            PreparedStatement st = cn.prepareStatement(query);
            putParameters(st, args);
            rows = st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rows;
    }

    @Override
    public int insert(String query, Object... args) {
        int id = -1;
        try (Connection cn = SqlConnector.getNewConnection()){
            PreparedStatement st = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            putParameters(st, args);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            id = rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    private void putParameters(PreparedStatement st, Object...args) throws SQLException {
        for(int i = 0; i < args.length; i++){
           Object value = args[i];
           int index = i + 1;
           if(value instanceof String)
               st.setString(index, String.valueOf(value));
           if(value instanceof Integer)
               st.setInt(index, (int)value);
           if(value instanceof Date)
               st.setDate(index, new java.sql.Date(((Date)value).getTime()));
           if(value instanceof Float)
               st.setFloat(index, (float)value);
           if(value instanceof Double)
               st.setDouble(index, (double)value);
        }
    }
}
