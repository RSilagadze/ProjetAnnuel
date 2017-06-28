package dao;

import entities.Link;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class LinkDAO extends AbstractDAO<Link> {

    private Link getLinkData(ResultSet rs)  {
        Link l = new Link();
        try {
            l.setId(rs.getInt("Id"));
            l.setIdUser(rs.getInt("IdUser"));
            l.setUrl(rs.getString("Url"));
            l.setDateCreated(rs.getDate("DateCreated"));
            l.setName(rs.getString("Name"));
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return l;
    }

    public LinkDAO() {
        this.reader = this::getLinkData;
    }


}
