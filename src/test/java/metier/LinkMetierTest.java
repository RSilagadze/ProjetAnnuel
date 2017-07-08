package metier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ConfigLoader;
import utils.Queries;
import utils.Utils;

/**
 * Created by Romaaan on 08/07/2017.
 */
public class LinkMetierTest {

    @Before
    void prepareTestBase(){
        ConfigLoader.init("./webmodule/config_test.properties");
        String script = Utils.readFile("./webmodule/db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    public void getLinkListByUserId() throws Exception {
    }

    @Test
    public void deleteLink() throws Exception {
    }

    @Test
    public void insertLink() throws Exception {
    }

    @After
    void deleteTestBase(){
        Utils.executeQuery(Queries.dropDataBase);
    }

}