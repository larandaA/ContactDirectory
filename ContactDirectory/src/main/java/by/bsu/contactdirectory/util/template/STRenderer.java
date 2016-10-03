package by.bsu.contactdirectory.util.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexandra on 02.10.2016.
 */
public class STRenderer {

    public static Map<String, String> templates;

    static {
        templates = new HashMap<>();
        STGroup group = new STGroupFile("email_template.stg");

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
        templates.put("busy", busy.render());
    }

    public static String render(String templateName, String name, String text) {
        STGroup group = new STGroupFile("email_template.stg");
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
