package by.bsu.contactdirectory.util.file;

import by.bsu.contactdirectory.servlet.MainServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
            String newFn = "";
            try {
                int pos;
                if ((pos = fn.indexOf("files")) >= 0) {
                    newFn = fn.substring(pos);
                } else if ((pos = fn.indexOf("img/contacts")) >= 0) {
                    newFn = fn.substring(pos);
                } else {
                    continue;
                }
                if (newFn.endsWith("default.jpg")) {
                    continue;
                }
                File file = new File(MainServlet.appPath + newFn);
                if (file.exists() && !file.isDirectory()) {
                    file.delete();
                }
                logger.info(String.format("Deleted file: %s", newFn));
            } catch (SecurityException ex) {
                logger.warn(String.format("Can't delete file: %s", newFn));
            }
        }
    }
}
