package utils;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class Queries {

    private static final DataBase dataBase = new DataBase(ConfigLoader.getConfigProperties().getProperty("downloaderBase"));
    private static final DataBase.Table userTable = new DataBase.Table(ConfigLoader.getConfigProperties().getProperty("userTable"));
    private static final DataBase.Table linkTable = new DataBase.Table(ConfigLoader.getConfigProperties().getProperty("linkTable"));
    private static final DataBase.Table userTypeTable = new DataBase.Table(ConfigLoader.getConfigProperties().getProperty("userTypeTable"));

    public static final String getUserByLogPass = "SELECT u.* FROM " + dataBase + "." + userTable + " u WHERE u.Login=? AND u.Pass=?";
    public static final String getLinksByUser = "SELECT l.* FROM " + dataBase + "." + linkTable + " l WHERE l.IdUser=? Order By l.DateCreated DESC";
    public static final String getUserTypeByUserID = "SELECT t.* FROM " + dataBase + "." + userTypeTable + " t " +
           "INNER JOIN " + dataBase + "." + userTable + " u ON u.IdType = t.Id WHERE u.Id=?";
    public static final String deleteLinkByURL = "DELETE FROM " + dataBase + "." + linkTable + " WHERE URL=?";
    public static final String insertLink = "INSERT INTO " + dataBase + "." + linkTable + " (Url, IdUser, DateCreated, Name) " +
            "VALUES (?,?,?,?)";
    public static final String dropDataBase = "DROP DATABASE IF EXISTS "+ dataBase +";";
}
