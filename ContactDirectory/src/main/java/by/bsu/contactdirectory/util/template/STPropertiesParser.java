package by.bsu.contactdirectory.util.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.Attributes;

/**
 * Created by Alexandra on 03.10.2016.
 */
public class STPropertiesParser {

    private static final String STG_PATH_KEY = "stg.path";
    private static final String NAME_PARAM_KEY = "name.param";
    private static final String TEXT_PARAM_KEY = "text.param";

    private static final String DEFAULT_STG_PATH = "email_text.stg";
    private static final String DEFAULT_NAME_PARAM = "name";
    private static final String DEFAULT_TEXT_PARAM = "text";

    private static Logger logger = LogManager.getLogger(STPropertiesParser.class);


    static void parse(String pathToProperties) throws IOException {
        InputStream input = null;
        try {
            ClassLoader classLoader = STPropertiesParser.class.getClassLoader();
            input = classLoader.getResourceAsStream(pathToProperties);
            Properties properties = new Properties();
            properties.load(input);

            String stPath = properties.getProperty(STG_PATH_KEY);
            if (stPath == null || stPath.isEmpty()) {
                STRenderer.setStgPath(DEFAULT_STG_PATH);
                logger.debug("st.path is not set.");
            } else {
                STRenderer.setStgPath(stPath);
            }

            String nameParam = properties.getProperty(NAME_PARAM_KEY);
            if (nameParam == null || nameParam.isEmpty()) {
                STRenderer.setNameParam(DEFAULT_NAME_PARAM);
                logger.debug("name.param is not set.");
            } else {
                STRenderer.setNameParam(nameParam);
            }

            String textParam = properties.getProperty(TEXT_PARAM_KEY);
            if (textParam == null || textParam.isEmpty()) {
                STRenderer.setTextParam(DEFAULT_TEXT_PARAM);
                logger.debug("text.param is not set.");
            } else {
                STRenderer.setTextParam(textParam);
            }

        } finally {
            try {
                input.close();
            } catch (IOException e) {}
        }
    }
}
