package metier;

import entities.Link;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
    void prepareTestBase(){
        ConfigLoader.init(Const.WEBMODULE_PATH + "config_test.properties");
        String script = Utils.readFile(Const.WEBMODULE_PATH + "db_script.sql");
        Utils.executeQuery(script);
    }

    @Test
    @DisplayName("Testing number of links for user 1, should be 3")
    public void getLinkListByUserId() throws Exception {
        int linkCount = 3;
        List<Link> lstLink = LinkMetier.getLinkListByUserId(1);
        Assert.assertTrue("User's 1 links list count",linkCount == lstLink.size());

    }

    @Test
    @DisplayName("Testing deletion of the link with url 'test.com', should return 1")
    public void deleteLink() throws Exception {
        int affectedRows = 1;
        int actualRows = LinkMetier.deleteLink("test.com");
        Assert.assertTrue("Count of affected rows",affectedRows == actualRows);
    }

    @Test
    @DisplayName("Testing insertion of the link with url 'junit_test.com', should return its Id")
    public void insertLink() throws Exception {
       String url = "junit_test.com";
       String filename = "test";
       Date date = new Date();
       int idUser = 99;
       int id = LinkMetier.insertLink(url, idUser, date, filename);
       Assert.assertTrue("Id must be > 0 upon success",id > 0);
    }

    @After
    void deleteTestBase(){
        Utils.executeQuery(Queries.dropDataBase);
    }
}