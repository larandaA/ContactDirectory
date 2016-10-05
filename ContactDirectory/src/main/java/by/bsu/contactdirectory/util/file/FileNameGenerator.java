package by.bsu.contactdirectory.util.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class FileNameGenerator {

    private static Random random = new Random();

    public static String defaultPhotoPath;
    public static String filesPath;
    public static String photosPath;
    private static final String PROPERTIES_PATH = "file.properties";

    private static Logger logger = LogManager.getLogger(FileNameGenerator.class);

    static {
        try {
            logger.debug("Reading properties.");
            FilePropertiesParser.parse(PROPERTIES_PATH);
            logger.debug(String.format("Check folder: %s", photosPath));
            createFolderIfNotExist(photosPath);
            logger.debug(String.format("Check folder: %s", filesPath));
            createFolderIfNotExist(filesPath);
        } catch (SecurityException | IOException ex) {
            logger.fatal(ex);
            throw new RuntimeException(ex);
        }
    }

    static void setFilesPath(String path) {
        filesPath = path;
    }

    static void setPhotosPath(String path) {
        photosPath = path;
    }

    static void setDefaultPhotoPath(String path) {
        defaultPhotoPath = path;
    }

    private static void createFolderIfNotExist(String path) {
        File folder = new File(path);
        logger.debug(String.format("Checking folder: %s", folder.getAbsolutePath()));
        if (!folder.exists() || !folder.isDirectory()) {
            logger.debug(String.format("Creating folder: %s", folder.getAbsolutePath()));
            folder.mkdir();
            logger.debug(String.format("Created folder: %s", folder.getAbsolutePath()));
        }
    }

    public static String generatePhotoFileName(String fileExtension) {
        String filename = "";
        if (fileExtension.indexOf('.') == -1) {
            fileExtension = "." + fileExtension;
        }
        boolean exists = true;
        while(exists) {
            filename = photosPath + generateInt() + fileExtension;
            File file = new File(filename);
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
            filename = filesPath + generateInt() + fileExtension;
            File file = new File(filename);
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
