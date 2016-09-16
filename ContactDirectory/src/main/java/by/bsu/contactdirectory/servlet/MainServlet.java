package by.bsu.contactdirectory.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.annotation.WebServlet;

import by.bsu.contactdirectory.connectionpool.ConnectionPool;
import by.bsu.contactdirectory.dailymailing.DailyMailingStarter;
import by.bsu.contactdirectory.util.email.EmailSender;

/**
 * Created by Alexandra on 04.09.2016.
 */
//@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    public void init()throws ServletException {
        super.init();
        ConnectionPool.start(getServletContext().getRealPath("WEB-INF/resources/db.properties"));
        EmailSender.init(null, null, null, null);
        DailyMailingStarter.start();

    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   		String action = req.getServletPath();
   		ActionInvoker.invoke(action, req, res);    	
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().close();
    }
}
