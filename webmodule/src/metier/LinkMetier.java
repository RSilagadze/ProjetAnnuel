package metier;

import dao.LinkDAO;
import entities.Link;
import utils.Queries;

import java.util.List;

/**
 * Created by Romaaan on 28/06/2017.
 */
public class LinkMetier {

    private static final LinkMetier instance = new LinkMetier();
    private final LinkDAO linkDAO;

    private LinkMetier(){
        linkDAO = new LinkDAO();
    }

    public static List<Link> getLinkListByUserId(int id){
        return instance.linkDAO.getList(Queries.getLinksByUser, id);
    }

    public static int deleteLink(String url){
        return instance.linkDAO.delete(Queries.deleteLinkByURL, url);
    }

}
