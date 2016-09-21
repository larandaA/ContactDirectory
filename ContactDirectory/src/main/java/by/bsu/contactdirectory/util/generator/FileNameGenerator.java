package by.bsu.contactdirectory.util.generator;

import java.io.File;
import java.util.Random;

/**
 * Created by Alexandra on 20.09.2016.
 */
public class FileNameGenerator {

    private static Random random = new Random();

    private static String contactImagesFolder = "img/contacts/";

    public static String generatePhotoFileName(String fileExtension) {
        String filename = "";
        if (fileExtension.isEmpty() || fileExtension.indexOf('.') == -1) {
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

    private static int generateInt() {
        return random.nextInt();
    }
}
