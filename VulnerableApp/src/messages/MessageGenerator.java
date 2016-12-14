package messages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import handlers.UploadHandler;
import model.Constants;
import model.SessionUser;
import model.UserFeedback;
import model.UserProfileInfo;

public class MessageGenerator {
	private static Logger logger = LoggerFactory.getLogger(UploadHandler.class);

	private static MessageGenerator instance = new MessageGenerator();

	public static MessageGenerator getInstance(){
		return instance;
	}

	public String profileInfoHasNotChanged(){
		return "";
	}
	
	// not used
	public String inputErrorsMessage(List<String> errors){
		StringBuffer msg = new StringBuffer("{\"").append(Constants.ATTRIBUTE_ACTION_TYPE).append("\":\"").
				append(Constants.JSON_INPUT_ERRORS).append("\",\"").append(Constants.JSON_LIST).append("\":[");
		for (String error : errors){
			msg.append("\"").append(error).append("\",");
		}
		msg.append("null]}");
		return msg.toString();
	}
	
	
	// returns redirect message
	public String redirectMessage(String destination){
		StringBuffer msg = new StringBuffer("{\"").append(Constants.ATTRIBUTE_ACTION_TYPE).append("\":\"").
				append(Constants.JSON_REDIRECT).append("\",\"").append(Constants.JSON_LOCATION).append("\":\"").
				append(destination).append("\"}");
		return msg.toString();
	}

	// build a JSON message with the user's profile
	public String userProfileInfoMessage(UserProfileInfo userProfile, SessionUser user) {

		String username = user.getUsername();
		if(username!=null)
			username = "\""+username+"\"";

		String city = userProfile.getCity();
		if(city!=null)
			city = "\""+city+"\"";

		String country = userProfile.getCountry();
		if(country!=null)
			country = "\""+country+"\"";

		String work = userProfile.getProfession();
		if(work!=null)
			work = "\""+work+"\"";

		String email = user.getEmail();
		if(email!=null)
			email = "\""+email+"\"";

		StringBuffer msg = new StringBuffer("{\"").append(Constants.ATTRIBUTE_ACTION_TYPE).append("\":\"").
				append(Constants.TYPE_GET_PROFILE_INFO).append("\",\"").append(Constants.JSON_USERNAME).append("\":").
				append(username).append(",\"").append(Constants.JSON_CITY).append("\":").
				append(city).append(",\"").append(Constants.JSON_COUNTRY).append("\":").
				append(country).append(",\"").append(Constants.JSON_WORK).append("\":").
				append(work).append(",\"").append(Constants.JSON_EMAIL).append("\":").
				append(email).append("}");
		return msg.toString();
	}

	// return a list of feedback
	public String feedbackListMessage(List<UserFeedback> feedback) {
		StringBuffer msg = new StringBuffer("{\"").append(Constants.TYPE_FEEDBACKS).append("\":[ ");
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UserFeedback feed : feedback){
			Date when;
			if(feed.getDate()!=null)
				when = new Date(feed.getDate().getTime());
			else
				when = new Date(System.currentTimeMillis());
			String type = feed.getType();
			String message = feed.getMessage();
			Integer idUser = feed.getIdUser();

			msg.append("{\"").append(Constants.JSON_FEEDBACK_TYPE).append("\":\"").append(type).append("\",\"").
			append(Constants.JSON_FEEDBACK_MESSAGE).append("\":\"").append(message).append("\",\"").
			append(Constants.JSON_USR).append("\":\"").append(idUser).append("\",\"").
			append(Constants.JSON_FEEDBACK_DATE).append("\":\"").append(dfm.format(when)).append("\"},");
		}
		msg.append("{} ] }");
		logger.debug("Returning the following JSON feedback list:\n"+msg.toString());
		return msg.toString();
	
	}
	
	// profile info has changed message
	public String profileInfoHasChanged() {

		StringBuffer msg = new StringBuffer("{\"").append(Constants.ATTRIBUTE_ACTION_TYPE).append("\":\"").
				append(Constants.JSON_PROFILE_INFO_CHANGED).append("\"}");
		return msg.toString();
	}


	// return an anti-CSRF token
	public String csrfToken(String token) {
		StringBuffer msg = new StringBuffer("{\"").append(Constants.CSRF_TOKEN).append("\":\"").
				append(token).append("\"}");
		return msg.toString();
	}

}
