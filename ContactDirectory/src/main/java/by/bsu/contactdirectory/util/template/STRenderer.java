package by.bsu.contactdirectory.util.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandra on 02.10.2016.
 */
public class STRenderer {

    public static Map<String, String> templates;
    private static String stgPath;
    private static String nameParam;
    private static String textParam;

    public static void setStgPath(String stgPath) {
        STRenderer.stgPath = stgPath;
    }

    public static void setNameParam(String nameParam) {
        STRenderer.nameParam = nameParam;
    }

    public static void setTextParam(String textParam) {
        STRenderer.textParam = textParam;
    }

    private static Logger logger = LogManager.getLogger(STRenderer.class);

    static {
        try {
            STPropertiesParser.parse("st.properties");

            templates = new HashMap<>();
            STGroup group = new STGroupFile(stgPath);

            Set<String> stSet = group.getTemplateNames();

            for (String stName : stSet) {
                String name = stName.substring(1);
                ST st = group.getInstanceOf(name);
                st.add(nameParam, '<' +nameParam + '>');
                st.add(textParam, '<' + textParam + '>');
                templates.put(name, st.render());
            }
            logger.info("String templates checked.");
        } catch (IOException ex) {
            logger.error(ex);
        }

    }

    public static String render(String templateName, String name, String text) {
        STGroup group = new STGroupFile(stgPath);
        try {
            ST st = group.getInstanceOf(templateName);
            st.add(nameParam, name);
            st.add(textParam, text);
            logger.info(String.format("Rendered: %s.", st.getName()));
            return st.render();
        } catch (IllegalArgumentException ex) {
            logger.warn(String.format("Requested st not found: %s", templateName));
            return text;
        }
    }
}
