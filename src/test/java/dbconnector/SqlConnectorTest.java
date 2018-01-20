package dbconnector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.Const;
import utils.ConfigLoader;

import java.sql.Connection;

/**
 * Created by Romaaan on 13/07/2017.
 */
public class SqlConnectorTest {

    @Before
    public void prepareTestBase() {
        ConfigLoader.init(Const.WEBMODULE_PATH + "config_test.properties");
    }

    @Test
    public void getNewConnection() throws Exception {
        Connection cn = SqlConnector.getNewConnection();
        Assert.assertTrue("SqlConnection is not null or closed", cn != null && !cn.isClosed());
    }
   /* @Test
    public void badTest(){
        Assert.assertTrue(false);
    }*/

}