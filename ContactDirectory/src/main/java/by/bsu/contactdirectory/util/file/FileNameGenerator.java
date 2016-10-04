package by.bsu.contactdirectory.util.file;

import by.bsu.contactdirectory.servlet.MainServlet;
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
    public static final String BASE_FOLDER = "/files/";
    private static final String PROPERTIES_PATH = "file.properties";

    private static Logger logger = LogManager.getLogger(FileNameGenerator.class);

    static {
        try {
            FilePropertiesParser.parse(PROPERTIES_PATH);
            createFolderIfNotExist(MainServlet.appPath + BASE_FOLDER);
            createFolderIfNotExist(MainServlet.appPath + BASE_FOLDER + photosPath);
            createFolderIfNotExist(MainServlet.appPath + BASE_FOLDER + filesPath);
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
            filename = BASE_FOLDER + photosPath + generateInt() + fileExtension;
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
            filename = BASE_FOLDER + filesPath + generateInt() + fileExtension;
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
