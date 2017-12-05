package entities;

import java.util.Date;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class User extends DefaultEntity {

    private int id;
    private int idType;
    private String name;
    private String lastName;
    private String login;
    private String pass;
    private Date dateRegister;
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private UserType userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User(){
        this.userType = new UserType();
    }

    @Override
    protected Object getKeyComparer() {
        return this.id;
    }

    @Override
    public boolean isEmpty() {
        return this.id <= 0 ;
    }
}
