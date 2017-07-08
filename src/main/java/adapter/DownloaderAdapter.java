package adapter;

import Download.Downloader;
import entities.Link;
import interfaces.IPostback;
import metier.LinkMetier;
import tools.Const;

/**
 * Created by kokoghlanian on 28/06/2017.
 */
public class DownloaderAdapter {

    private static final DownloaderAdapter instance = new DownloaderAdapter();
    private final IPostback<Downloader> ipostback;

    private DownloaderAdapter(){
        this.ipostback = (downloader) -> LinkMetier.deleteLink(downloader.getHost());
    }

    public static Downloader linkToDownloader(Link link){
        return new Downloader(Const.DEFAULT_PATH, link.getName(), link.getUrl(), instance.ipostback);
    }

}
