package com.vulnerableapp.results;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigurationUtil implements ServletContextListener {

	private Properties config = new Properties();
	private static String user = "" ;
	private static String password = "" ;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.CONFIG_FILE));		
			ConfigurationUtil.password = config.getProperty("pwd");
			ConfigurationUtil.user = config.getProperty("usr");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	public static String getPassword(){
		return ConfigurationUtil.password;
	}
	public static String getUsername(){
		return ConfigurationUtil.user;
	}
	
}