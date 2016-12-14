package model;

public final class Constants {

	/**
	   The caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	 */
	private Constants(){
	}
	
	public static final String CONFIG_FILE = "config.properties";
	public static final String JSON_INPUT_ERRORS = "inputErrors";

	public static final String VALIDATOR_NOT_NULL ="NOT_NULL";
	public static final String VALIDATOR_EMAIL = "EMAIL";
	public static final String VALIDATOR_USERNAME = "USERNAME";
	public static final String VALIDATOR_PASSWORD = "PASSWORD";
	public static final String VALIDATOR_CREDIT_CARD = "CREDIT_CARD";
	public static final String VALIDATOR_CSRF_TOKEN = "CSRF_TOKEN";

	
	public static final String JSON_FILE = "file";

	public static final String SECURITY_CONTEXT = "SECURITY_CONTEXT";
	public static final String CSRF_TOKEN = "token";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ATTRIBUTE_JSON_EMAIL = "email";

	public static final String ATTRIBUTE_JSON_DATA = "json";

	/** ATTRIBUTE TYPES **/
	public static final String ATTRIBUTE_SECURITY_CONTEXT = "SECURITY_CONTEXT";
	public static final String ATTRIBUTE_ACTION_TYPE = "type";
	public static final String ATTRIBUTE_JSON = "json";
	public static final String ATTRIBUTE_VALIDATION_ERRORS = "errors";
	public static final String ATTRIBUTE_REDIRECT_TYPE = "redirectType";

	/** EMAIL CHECK HANDLER IN **/

	public static final String PARAMETER_EMAIL = "email";
	public static final String PARAMETER_TOKEN = "token";
	public static final Object PARAMETER_EMAIL_CHANGE = "changeEmail";

	
	/** EMAIL CHECK HANDLER OUT **/
	public static final String JSON_TOKEN_GENERATED = "tokenGenerated";
	public static final String JSON_TOKEN_NOT_GENERATED = "tokenNotGenerated";
	public static final String JSON_EMAIL_CHANGED = "emailChanged";
	public static final String JSON_EMAIL_NOT_CHANGED = "emailNotChanged";


	public static final String JSON_POST_TEXT = "text";
	public static final String JSON_POST_TITLE = "title";
	/** ACTION TYPES IN */


	public static final String TYPE_CHANGE_PROFILE_INFO = "changeProfileInfo";
	public static final String TYPE_GET_PROFILE_INFO = "userProfileInfo";
	public static final String TYPE_CHANGE_ACCOUNT_INFO = "changeAccountInfo";


	/** CHANGE USER PROFILE INFO OUT */
	public static final String JSON_PROFILE_INFO_CHANGED = "profileInfoChanged";
	public static final String JSON_ACCOUNT_INFO_CHANGED = "accountInfoChanged";	
	public static final String JSON_ACCOUNT_INFO_NOT_CHANGED = "accountInfoNotChanged";	
	
	/** SIGNUP ACTION IN */
	public static final String JSON_USERNAME = "username";
	public static final String JSON_EMAIL = "email";
	public static final String JSON_PASSWORD = "password";


	public static final String JSON_PARAMETER = "parameter";
	
	/** POSTSIGNUP ACTION IN */

	public static final String JSON_BIRTH = "birth";
	public static final String JSON_SEX = "sex";
	public static final String JSON_CITY = "city";
	public static final String JSON_COUNTRY = "country";
	public static final String JSON_PHOTO = "photo";
	public static final String JSON_WORK = "work";
	public static final String JSON_WORKPLACE = "workplace";
	public static final String JSON_LANGUAGE = "language";
	
	/** STATUS BASED REDIRECT ACTION OUT */
	public static final String JSON_REDIRECT = "redirect";
	public static final String JSON_LOCATION = "location";

	
	/** ESAPI VALIDATION TYPES */
	public static final String ESAPI_EMAIL = "Email";
	public static final String EXP_REG_PASSWORD = "^(?=.*\\d).{6,20}$";



	/** JSON MESSAGES OUT */
	public static final String JSON_LIST = "list";
	public static final String JSON_USR = "usr";
	

	
	public static final String TYPE_STORE_USR_FEEDBACK = "storeFeedback";
	public static final String TYPE_GET_ALL_FEEDBACKS = "getFeedbacks";
	public static final String TYPE_FEEDBACKS = "feedbacks";


	public static final String JSON_THEN = "then";
	public static final String JSON_SEND = "send";
	public static final String JSON_GET = "get";

	public static final String PARAMETER_USER_ID = "userId";
	public static final String JSON_PASSWORD_CHANGED = "pwdChanged";
	public static final String JSON_PASSWORD_NOT_CHANGED = "pwdNotChanged";
	
	public static final String JSON_FEEDBACK_MESSAGE = "msg";
	public static final String JSON_FEEDBACK_DATE = "date";
	public static final String JSON_FEEDBACK_TYPE = "area";
	public static final String ATTRIBUTE_SECURITY_ROLE = "SECURITY_ROLE";
	public static final String TYPE_GET_USR_FEEDBACK = "getFeedbacks";
	public static final String TYPE_READ_DOCUMENTS = "getDocs";
	public static final String TYPE_GET_CSRF_TOKEN = "token";
	public static final String TYPE_READ_POSTS = "getPosts";
	public static final String TYPE_STORE_DOCUMENT = "storeDoc";
	public static final String TYPE_STORE_POST = "storePost";
	public static final String JSON_POST_CONTENT = "content";
	public static final String TYPE_DELETE_POST = "deletePost";
	public static final String FAILURE = "ko";
	public static final String SUCCESS = "ok";


}