package tools;

import java.io.IOException;


public class CreateUserSite {
    private static final String WIN_ID = "Windows";
    private static final String WIN_PATH = "rundll32";
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
    private static final String UNIX_PATH = "netscape";
    private static final String UNIX_FLAG = "-remote openURL";
    private static final String url="http://vartankoko-001-site1.dtempurl.com/Subscribe";

    public static void displayURL() {

        boolean windows = isWindowsPlatform();
        String cmd = null;
        try {
            if (windows) {
                cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
                Process p = Runtime.getRuntime().exec(cmd);
            } else {
                cmd = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")";
                Process p = Runtime.getRuntime().exec(cmd);
                try {
                    int exitCode = p.waitFor();
                    if (exitCode != 0) {

                        cmd = UNIX_PATH + " " + url;
                        p = Runtime.getRuntime().exec(cmd);
                    }
                } catch (InterruptedException x) {
                    System.err.println("Error bringing up browser, cmd='" +
                            cmd + "'");
                    System.err.println("Caught: " + x);
                }
            }
        } catch (IOException x) {
            System.err.println("Could not invoke browser, command=" + cmd);
            System.err.println("Caught: " + x);
        }
    }

    public static boolean isWindowsPlatform() {
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(WIN_ID))
            return true;
        else
            return false;

    }



}
