package dev.dynamic;

import java.io.File;
import java.net.URISyntaxException;

public class WDirManager {

    private static String WDIR;

    public static String getCurrentWdir() {
        return WDIR;
    }

    public static void setWdir(String wdir) {
        WDIR = wdir;
    }

    public static String getExecutionDirectory() {
        try {
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return jarFile.getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
