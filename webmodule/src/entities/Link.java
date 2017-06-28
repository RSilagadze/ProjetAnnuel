package entities;

import java.util.Date;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class Link extends DefaultEntity {

    private int id;
    private String url;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    private String nameStr;
    private Date dateCreated;
    private int idUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    protected Object getKeyComparer() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
