package utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.Const;

import static org.junit.Assert.*;

/**
 * Created by Romaaan on 13/07/2017.
 */
public class UtilsTest {

   /* private final String query = "UPDATE jdownloader_test.Link SET URL='Updated_Test' WHERE Id=1";

    @Before
    public void prepareTestBase(){
        ConfigLoader.init(Const.WEBMODULE_PATH +"config_test.properties");
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    public void readFile() throws Exception {
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        int size_bytes = 2620;
        Assert.assertTrue("Shall return true if file size is 2701", size_bytes == script.getBytes().length);
    }

    @Test
    public void executeQuery() throws Exception {
       int rows = Utils.executeQuery(query);
        Assert.assertTrue("Shall return 1 upon successful update", rows == 1);
    }

    @After
    public void deleteTestBase(){
        Utils.executeQuery(Queries.dropDataBase);
    }
*/
}