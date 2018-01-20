package dao;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO() {
        this.reader = this::getUserData;
    }

    private User getUserData(ResultSet rs) throws SQLException {
        User u = new User();
        u.setMail(rs.getString("Mail"));
        u.setId(rs.getInt("Id"));
        u.setIdType(rs.getInt("IdType"));
        u.setName(rs.getString("Prenom"));
        u.setLastName(rs.getString("Nom"));
        u.setLogin(rs.getString("Login"));
        u.setPass(rs.getString("Pass"));
        u.setDateRegister(rs.getDate("DateRegister"));
        return u;
    }
}
