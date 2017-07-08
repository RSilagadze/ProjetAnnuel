package usercontrol;

import dbconnector.SqlConnector;
import entities.User;
import entities.UserType;
import utils.ConfigLoader;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class Context {

    private User currentUser;

    private static final Context instance = new Context();

    public static User getCurrentUser() {
        return instance.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        instance.currentUser = currentUser;
    }

    public static Context getInstance() {
        return instance;
    }

    private Context (){
        ConfigLoader.init("./webmodule/config.properties");
        currentUser = new User();
        currentUser.setUserType(new UserType());
    }
}
