package metier;

import entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.Const;
import utils.ConfigLoader;
import utils.Queries;
import utils.Utils;

/**
 * Created by Romaaan on 08/07/2017.
 */
public class UserMetierTest {
    @Before
    public void prepareTestBase(){
        ConfigLoader.init(Const.WEBMODULE_PATH +"config_test.properties");
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setIdType(1);
        user.setLastName("NomTest");
        user.setName("Test");
        user.setLogin("123");
        user.setPass("123");
        User actualUser = UserMetier.getUser(user.getLogin(), user.getPass());
        boolean condition = actualUser.getId() == user.getId() && actualUser.getIdType() == user.getIdType() &&
                            actualUser.getLastName().equals(user.getLastName()) && actualUser.getName().equals(user.getName());
        Assert.assertTrue("User shall be : Id = 1, IdType = 1, Name = Test, LastName = NomTest", condition);
    }

    @After
    public void deleteTestBase(){
        Utils.executeQuery(Queries.dropDataBase);
    }

}