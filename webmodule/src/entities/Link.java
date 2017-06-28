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

    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    protected Object getKeyComparer() {
        return this.id;
    }

    @Override
    public boolean isEmpty() {
        return this.id <= 0;
    }
}
