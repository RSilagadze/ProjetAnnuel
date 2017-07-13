package entities;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class UserType extends DefaultEntity {

    private int id;
    private String nameStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    @Override
    protected Object getKeyComparer() {
        return id;
    }

    @Override
    public boolean isEmpty() {
        return id <= 0;
    }
}
