package by.bsu.contactdirectory.util.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexandra on 02.10.2016.
 */
public class STRenderer {

    public static Map<String, String> templates;
    private static final String STG_FILENAME = "email_text.stg";
    private static final String NAME = "name";
    private static final String TEXT = "text";

    static {
        templates = new HashMap<>();
        STGroup group = new STGroupFile(STG_FILENAME);

        Set<String> stSet = group.getTemplateNames();

        for (String stName : stSet) {
            String name = stName.substring(1);
            ST st = group.getInstanceOf(name);
            st.add(NAME, '<' + NAME + '>');
            st.add(TEXT, '<' + TEXT + '>');
            templates.put(name, st.render());
        }
/*
        ST hello = group.getInstanceOf("hello");
        hello.add("name", "<name>");
        hello.add("text", "<text>");
        templates.put("hello", hello.render());

        ST happy_bd = group.getInstanceOf("happy_bd");
        happy_bd.add("name", "<name>");
        happy_bd.add("text", "<text>");
        templates.put("happy_bd", happy_bd.render());

        ST sorry = group.getInstanceOf("sorry");
        sorry.add("name", "<name>");
        sorry.add("text", "<text>");
        templates.put("sorry", sorry.render());

        ST busy = group.getInstanceOf("busy");
        busy.add("name", "<name>");
        busy.add("text", "<text>");
        templates.put("busy", busy.render());*/
    }

    public static String render(String templateName, String name, String text) {
        STGroup group = new STGroupFile(STG_FILENAME);
        try {
            ST st = group.getInstanceOf(templateName);
            st.add("name", name);
            st.add("text", text);
            return st.render();
        } catch (IllegalArgumentException ex) {
            return text;
        }
    }
}
