package utils;

import java.io.File;

public class Utils {
    public static String scriptsAbsolutePath() {
        String scriptsAbsPath = new File(".").getAbsolutePath();
        return scriptsAbsPath.substring(0, scriptsAbsPath.length() - 1) + "scripts/";
    }
}
