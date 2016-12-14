package utils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Constants;

public class ConfigurationUtil implements ServletContextListener {

	private Properties config = new Properties();
	private static String dbUri = "" ;
	private static String dbUser = "" ;
	private static String dbPassword = "" ;
	private static String usersDataPath = "";
	private static String usersXMLPath = "";
	private static Logger logger = LoggerFactory.getLogger(ConfigurationUtil.class);
	private static String baseHost;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.CONFIG_FILE));		
			ConfigurationUtil.dbPassword = config.getProperty("db_password");
			ConfigurationUtil.dbUser = config.getProperty("db_user");
			ConfigurationUtil.dbUri = config.getProperty("db_uri");
			ConfigurationUtil.usersDataPath = config.getProperty("users_data_path");
			ConfigurationUtil.usersXMLPath = config.getProperty("users_xml_path");
			ConfigurationUtil.baseHost = config.getProperty("base_host");
			logger.debug("Users Data Path: "+ConfigurationUtil.getUsersDataPath());
			logger.debug("Users XML Path: "+ConfigurationUtil.getUsersXMLPath());
			logger.debug("Base Host: "+ConfigurationUtil.getBaseHost());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
	public static String getUsersDataPath(){
		return ConfigurationUtil.usersDataPath;
	}
	public static String getUsersXMLPath() {
		return ConfigurationUtil.usersXMLPath;

	}
	public static String getDBPassword(){
		return ConfigurationUtil.dbPassword;
	}
	public static String getDBUser(){
		return ConfigurationUtil.dbUser;
	}
	public static String getDBURI(){
		return ConfigurationUtil.dbUri;
	}
	public static String getBaseHost() {
		return ConfigurationUtil.baseHost;
	}
}