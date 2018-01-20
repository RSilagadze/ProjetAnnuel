package controller;

import Download.Downloader;

/**
 * Created by kokoghlanian on 07/06/2017.
 */
public class ControllerMediator implements IControllerMediator {

    private AddLinkController addLinkController;
    private MainWindowController mainWindowController;

    private ControllerMediator() {
    }

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    public void registerMainWindowController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    public AddLinkController getAddLinkController() {
        return addLinkController;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void registerAddLinkController(AddLinkController controller) {
        this.addLinkController = controller;
    }

    public void executeDownload(Downloader downloader) {
        mainWindowController.launchDownload(downloader);
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }


}
