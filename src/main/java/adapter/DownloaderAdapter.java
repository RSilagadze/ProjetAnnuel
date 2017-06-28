package adapter;

import Download.Downloader;
import entities.Link;
import tools.Const;

/**
 * Created by kokoghlanian on 28/06/2017.
 */
public class DownloaderAdapter {

    public static Downloader linkToDownloader(Link link){
        return new Downloader(Const.DEFAULT_PATH,link.getName(),link.getUrl());
    }
}
