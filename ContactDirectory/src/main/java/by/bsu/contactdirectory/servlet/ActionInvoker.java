package by.bsu.contactdirectory.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.bsu.contactdirectory.action.Action;
import by.bsu.contactdirectory.action.GetFileAction;
import by.bsu.contactdirectory.action.StartPageAction;

public class ActionInvoker {
	
	private static HashMap<String, Action> actions = new HashMap<>();
	
	static {
		actions.put(Actions.START_PAGE, new StartPageAction());
		actions.put(Actions.GET_FILE, new GetFileAction());
	}
	
	public static void invoke(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Action handler = actions.get(action);
		if (handler == null) {
			try {
				Class cl = Class.forName("by.bsu.contactdirectory.action." + action.substring(1) + "Action");
				Action newHandler = (Action)cl.newInstance();
				actions.put(action, newHandler);
				newHandler.execute(request, response);
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
				actions.get(Actions.START_PAGE).execute(request, response);
			}
		} else {
			handler.execute(request, response);
		}
	}

}
