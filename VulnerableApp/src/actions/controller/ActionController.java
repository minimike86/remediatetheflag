package actions.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actions.IAction;
import actions.InputErrorAction;
import model.Constants;

public class ActionController {

	private static Logger logger = LoggerFactory.getLogger(ActionController.class);

	protected Map<String, String> type2action;

	public ActionController(Map<String, String> actionsMap) {
		type2action = actionsMap;
	}

	public void executeAction(HttpServletRequest request, HttpServletResponse response) throws Exception{

		String data = request.getReader().readLine();
		if (data != null) {
			try {
				JSONObject json = (JSONObject) new JSONParser().parse(data);
				String className = type2action.get((String) json.get(Constants.ATTRIBUTE_ACTION_TYPE));

				if(className!=null){
					request.setAttribute(Constants.ATTRIBUTE_JSON, json);
					try {
						IAction action = ((IAction) Class.forName(className).newInstance());
						logger.debug("#Executing: "+className);
						action.doAction(request,response);

					} catch (InstantiationException | IllegalAccessException| ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else{
					logger.debug("### "+json.get("type")+" DIDN'T pass validation");
					IAction action = new InputErrorAction();
					action.doAction(request, response);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
	}
}