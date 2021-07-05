package utils;

import java.io.File;

public class Utils {
    public static String scriptsAbsolutePath() {
        String scriptsAbsPath = new File(".").getAbsolutePath();
        return scriptsAbsPath.substring(0, scriptsAbsPath.length() - 1) + "scripts/";
    }

    public static String tempFolderPath() {
        return System.getProperty("user.home") + "/tests/santiago/temp/";
    }
}
