package by.bsu.contactdirectory.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Alexandra on 30.09.2016.
 */
@WebFilter(filterName = "CharsetFilter", urlPatterns = "/*")
public class CharsetFilter implements Filter {

    private static Logger logger = LogManager.getLogger(CharsetFilter.class);
    private String encoding;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(encoding);
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
        logger.info("Work");
    }

    public void init(FilterConfig config) throws ServletException {
        encoding = "UTF-8";
    }

}
