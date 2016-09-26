package by.bsu.contactdirectory.util.file;

import by.bsu.contactdirectory.servlet.MainServlet;

import java.io.File;
import java.util.Random;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class FileNameGenerator {

    private static Random random = new Random();

    private static String contactImagesFolder = MainServlet.appPath + "img/contacts/";
    private static String contactAttsFolder = MainServlet.appPath + "files/";

    public static String generatePhotoFileName(String fileExtension) {
        String filename = "";
        if (fileExtension.indexOf('.') == -1) {
            fileExtension = "." + fileExtension;
        }
        boolean exists = true;
        while(exists) {
            filename = contactImagesFolder + generateInt() + fileExtension;
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
            filename = contactAttsFolder + generateInt() + fileExtension;
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
