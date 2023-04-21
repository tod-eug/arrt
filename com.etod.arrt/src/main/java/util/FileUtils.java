package util;

import java.io.File;

public class FileUtils {

    public static final String reportsFolder = "reports/";

    public File writePdfFile(String fileName) {
        File dir = new File(reportsFolder);
        boolean isCreated = dir.mkdirs();
        File file = new File(reportsFolder + fileName);
        return file;
    }
}
