package by.bsu.contactdirectory.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Alexandra on 03.10.2016.
 */
public class FilePropertiesParser {

    private static final String FILES_PATH_KEY = "files.path";
    private static final String PHOTOS_PATH_KEY = "photos.path";
    private static final String DEFAULTPHOTO_PATH_KEY = "defaultphoto.path";

    private static final String DEFAULT_FILES_PATH = "files/";
    private static final String DEFAULT_PHOTOS_PATH = "img/";
    private static final String DEFAULT_DEFAULTPHOTO_PATH = "default.jpg";

    static void parse(String pathToProperties) throws IOException {
        InputStream input = null;
        try {
            ClassLoader classLoader = FilePropertiesParser.class.getClassLoader();
            input = classLoader.getResourceAsStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(input);

            String filesPath = properties.getProperty(FILES_PATH_KEY);
            if (filesPath == null || filesPath.isEmpty()) {
                FileNameGenerator.setFilesPath(DEFAULT_FILES_PATH);
            } else {
                FileNameGenerator.setFilesPath(filesPath);
            }

            String photosPath = properties.getProperty(PHOTOS_PATH_KEY);
            if (photosPath == null || photosPath.isEmpty()) {
                FileNameGenerator.setPhotosPath(DEFAULT_PHOTOS_PATH);
            } else {
                FileNameGenerator.setPhotosPath(photosPath);
            }
            String defaultPhotoPath = properties.getProperty(DEFAULTPHOTO_PATH_KEY);
            if (defaultPhotoPath == null || defaultPhotoPath.isEmpty()) {
                FileNameGenerator.setDefaultPhotoPath(DEFAULT_DEFAULTPHOTO_PATH);
            } else {
                FileNameGenerator.setDefaultPhotoPath(defaultPhotoPath);
            }

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {}
            }
        }
    }
}
