package usercontrol;

import entities.User;
import entities.UserType;
import tools.Const;
import utils.ConfigLoader;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class Context {

    private static final Context instance = new Context();
    private static int value = 0;
    private User currentUser;

    private Context() {
        ConfigLoader.init(Const.WEBMODULE_PATH + "config.properties");
        currentUser = new User();
        currentUser.setUserType(new UserType());
        value = 1;
    }

    public static User getCurrentUser() {
        return instance.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        instance.currentUser = currentUser;
    }

    public static Context getInstance() {
        return instance;
    }
}
