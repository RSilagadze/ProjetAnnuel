package dao;

import entities.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class UserTypeDAO extends AbstractDAO<UserType> {

    private UserType getUserTypeData(ResultSet rs)  {
        UserType u = new UserType();
        try {
            u.setId(rs.getInt("Id"));
            u.setNameStr(rs.getString("NameStr"));
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return u;
    }

    public UserTypeDAO() {
        this.reader = this::getUserTypeData;
    }

}
