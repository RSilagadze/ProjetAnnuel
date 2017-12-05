package metier;

import entities.Link;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tools.Const;
import utils.ConfigLoader;
import utils.Queries;
import utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Romaaan on 08/07/2017.
 */
public class LinkMetierTest {

    @Before
    public void prepareTestBase(){
        ConfigLoader.init(Const.WEBMODULE_PATH + "config_test.properties");
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    public void getLinkListByUserId() throws Exception {
        int linkCount = 3;
        List<Link> lstLink = LinkMetier.getLinkListByUserId(1);
        Assert.assertTrue("User's 1 links list count",linkCount == lstLink.size());
    }

    @Test
    public void deleteLink() throws Exception {
        int affectedRows = 1;
        int actualRows = LinkMetier.deleteLink("test.com");
        Assert.assertTrue("Count of affected rows",affectedRows == actualRows);
    }

    @Test
    public void insertLink() throws Exception {
       String url = "junit_test.com";
       String filename = "test";
       Date date = new Date();
       int idUser = 99;
       int id = LinkMetier.insertLink(url, idUser, date, filename);
       Assert.assertTrue("Id must be > 0 upon success",id > 0);
    }

    @After
    public void deleteTestBase(){
        Utils.executeQuery(Queries.dropLinkTable);
        Utils.executeQuery(Queries.dropUserTable);
        Utils.executeQuery(Queries.dropUserTypeTable);
    }
}