package utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.Const;

import static org.junit.Assert.*;
import static utils.Queries.dataBase;

/**
 * Created by Romaaan on 13/07/2017.
 */
public class UtilsTest {

    @Before
    public void prepareTestBase(){
        ConfigLoader.init(Const.WEBMODULE_PATH +"config_test.properties");
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    public void readFile() throws Exception {
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        int size_bytes = 2654;
        Assert.assertTrue("Shall return true if file size is "+ size_bytes, size_bytes == script.getBytes().length);
    }

    @Test
    public void executeQuery() throws Exception {
        String query =  "UPDATE " + Queries.dataBase + ".link SET URL='Updated_Test' WHERE Id=1";
        int rows = Utils.executeQuery(query);
        Assert.assertTrue("Shall return 1 upon successful update", rows == 1);
    }

    @After
    public void deleteTestBase(){
        Utils.executeQuery(Queries.dropLinkTable);
        Utils.executeQuery(Queries.dropUserTable);
        Utils.executeQuery(Queries.dropUserTypeTable);
    }

}