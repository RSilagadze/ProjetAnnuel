package entities;

/**
 * Created by Romaaan on 27/06/2017.
 */
public enum UserTypeEnum {
    Free(1, "Free"),
    Premium(2, "Preminum"),
    Default (0, "Default");

    private final int id;
    private final String nameStr;

    UserTypeEnum(int id, String nameStr){
        this.id = id;
        this.nameStr = nameStr;
    }

    public int getId() {
        return id;
    }

    public String getNameStr() {
        return nameStr;
    }
}
