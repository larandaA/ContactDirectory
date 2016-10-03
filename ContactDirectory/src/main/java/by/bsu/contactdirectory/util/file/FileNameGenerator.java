package by.bsu.contactdirectory.util.file;

import by.bsu.contactdirectory.servlet.MainServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Random;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class FileNameGenerator {

    private static Random random = new Random();

    private static String contactImagesFolder = "img/contacts/";
    private static String contactAttsFolder = "files/";

    private static Logger logger = LogManager.getLogger(FileNameGenerator.class);

    static {
        try {
            createFolderIfNotExist(MainServlet.appPath + contactImagesFolder);
            createFolderIfNotExist(MainServlet.appPath + contactAttsFolder);
        } catch (SecurityException ex) {
            logger.fatal(ex);
            throw new RuntimeException(ex);
        }
    }

    private static void createFolderIfNotExist(String path) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }
    }

    public static String generatePhotoFileName(String fileExtension) {
        String filename = "";
        if (fileExtension.indexOf('.') == -1) {
            fileExtension = "." + fileExtension;
        }
        boolean exists = true;
        while(exists) {
            filename = contactImagesFolder + generateInt() + fileExtension;
            File file = new File(MainServlet.appPath + filename);
            if(!file.exists()) {
                exists = false;
            }
        }
        return filename;
    }

    public static String generateAttFileName(String fileExtension) {
        String filename = "";
        if (fileExtension.indexOf('.') == -1) {
            fileExtension = "." + fileExtension;
        }
        boolean exists = true;
        while(exists) {
            filename = contactAttsFolder + generateInt() + fileExtension;
            File file = new File(MainServlet.appPath + filename);
            if(!file.exists()) {
                exists = false;
            }
        }
        return filename;
    }

    private static int generateInt() {
        return random.nextInt() % 1000000000;
    }
}
