package controller;

import Download.Downloader;

/**
 * Created by kokoghlanian on 07/06/2017.
 */
public interface IControllerMediator {
    void registerMainWindowController(MainWindowController controller);

    void registerAddLinkController(AddLinkController controller);

    void executeDownload(Downloader downloader);
}
