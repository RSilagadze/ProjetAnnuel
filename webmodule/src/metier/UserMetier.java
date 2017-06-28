package metier;

import dao.UserDAO;
import dao.UserTypeDAO;
import entities.User;
import entities.UserType;
import utils.Queries;

/**
 * Created by Romaaan on 27/06/2017.
 */
public class UserMetier {

    private static final UserMetier instance = new UserMetier();
    private final UserDAO userDAO;
    private final UserTypeDAO typeDAO;

    private UserMetier(){
        userDAO = new UserDAO();
        typeDAO = new UserTypeDAO();
    }

    public static User getUser(String login, String pass){
        User u = instance.userDAO.get(Queries.getUserByLogPass, login, pass);
        if (u != null && !u.isEmpty()){
            UserType type = instance.typeDAO.get(Queries.getUserTypeByUserID, u.getId());
            u.setUserType(type);
        }else {
            u = new User();
            u.setUserType(new UserType());
        }
        return u;
    }



}
