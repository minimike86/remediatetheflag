package actions.controller;

import java.util.HashMap;
import java.util.Map;

import model.Constants;

public class UserActionsController extends ActionController {

	private static Map<String, String> type2action = new HashMap<String, String>();

	static {
		type2action.put(Constants.TYPE_READ_POSTS, "actions.GetPostsAction");
		type2action.put(Constants.TYPE_STORE_POST, "actions.StorePostAction");
		type2action.put(Constants.TYPE_READ_DOCUMENTS, "actions.GetDocumentsAction");
		type2action.put(Constants.TYPE_GET_PROFILE_INFO, "actions.GetProfileInfoAction");
		type2action.put(Constants.TYPE_CHANGE_PROFILE_INFO, "actions.StoreProfileInfoAction");
		type2action.put(Constants.TYPE_GET_USR_FEEDBACK, "actions.GetFeedbackAction");
		type2action.put(Constants.TYPE_STORE_USR_FEEDBACK, "actions.StoreFeedbackAction");
		type2action.put(Constants.TYPE_GET_CSRF_TOKEN, "actions.GetCSRFTokenAction");
		type2action.put(Constants.TYPE_DELETE_POST, "actions.DeletePostAction");
			
	}
	public UserActionsController() {
		super(type2action);
	}
}