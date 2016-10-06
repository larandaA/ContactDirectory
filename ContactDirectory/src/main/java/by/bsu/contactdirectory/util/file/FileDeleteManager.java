package by.bsu.contactdirectory.util.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Alexandra on 25.09.2016.
 */
public class FileDeleteManager {

    private static Logger logger = LogManager.getLogger(FileDeleteManager.class);

    public static void deleteFiles(List<String> filenames) {
        for (String fn : filenames) {
            if (fn == null) {
                continue;
            }
            try {
                if (fn.endsWith(FileNameGenerator.defaultPhotoPath)) {
                    continue;
                }
                File file = new File(fn);
                if (file.exists() && !file.isDirectory()) {
                    file.delete();
                }
                logger.info(String.format("Deleted file: %s", fn));
            } catch (SecurityException ex) {
                logger.warn(String.format("Can't delete file: %s", fn));
            }
        }
    }
}
