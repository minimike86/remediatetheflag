package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.MysqlUser;
import model.UserProfileInfo;
import persistence.MysqlDataSource;

public class UserDAO{

	private MysqlDataSource dataSource = new MysqlDataSource();
	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);


	public Integer usernameToIdUser(String username) throws Exception{
		Connection connection = this.dataSource.getConnection();
		String query  = "select idUser from users where username=?";
		PreparedStatement statement = null; 
		try {
			logger.debug("#SQL: "+query);
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.executeQuery();
			ResultSet result = statement.getResultSet(); 
			if(result.next()) {
				return result.getInt("idUser");	
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();

			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return -1;
	}

	public MysqlUser loginUser(String username, String password) throws Exception { 

		password = DigestUtils.sha512Hex(password);
		Connection connection = this.dataSource.getConnection();
		String query  = "select idUser, email from users where username='"+username+"' and password='"+password+"'";
		Statement statement = null; 
		MysqlUser user = null;

		if(username != null && !username.equals("") && password!=null && !password.equals("")){
			try {
				logger.debug("#SQL: "+query);
				statement = connection.createStatement();
				statement.execute(query);
				ResultSet result = statement.getResultSet(); 

				if(result.next()) {
					user = new MysqlUser();
					user.setUsername(username);
					user.setIdUser(result.getInt("idUser"));
					user.setEmail(result.getString("email"));	
				}
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			} finally {
				try {
					if (statement != null) 
						statement.close();
					if (connection!= null)
						connection.close();

				} catch (SQLException e) {
					throw new Exception(e.getMessage());
				}
			}
		}

		return user;
	}

	public MysqlUser getUser(Integer idUser) throws Exception {

		Connection connection = this.dataSource.getConnection();
		String query  = "select username, email from users where idUser="+idUser;
		Statement statement = null; 
		MysqlUser user = null;

		try {
			statement = connection.createStatement();
			logger.debug("#SQL: "+query);
			ResultSet result = statement.executeQuery(query);

			if(result.next()) {
				user = new MysqlUser();
				user.setIdUser(idUser);
				user.setUsername(result.getString("username"));
				user.setEmail(result.getString("email"));
			}

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return user;
	}

	public void changePassword(Integer idUser, String newPassword) throws Exception {

		String pwd = DigestUtils.sha512Hex(newPassword);
		Connection connection = dataSource.getConnection();
		Statement statement = null; 
		String query = "UPDATE users SET password='"+pwd+"' WHERE idUser="+idUser;

		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			statement.execute(query);

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}	
	}

	public void changeEmail(Integer idUser, String newEmail) throws Exception {

		Connection connection = dataSource.getConnection();
		Statement statement = null;
		String query = "UPDATE users SET email='"+newEmail+"' WHERE idUser="+idUser;
		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement(); 
			statement.execute(query); 
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}		
	}

	public UserProfileInfo getUserProfileInfo(Integer idUser) throws Exception {

		Connection connection = this.dataSource.getConnection();
		String query  = "SELECT * from userprofileinfo WHERE idUser="+idUser;
		Statement statement = null; 
		UserProfileInfo info = new UserProfileInfo();

		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			statement.execute(query);
			ResultSet result = statement.getResultSet();

			if(result.next()) {
				info.setCity(result.getString("city"));
				info.setCountry(result.getString("country"));
				info.setIdUser(result.getInt("idUser"));
				info.setProfession(result.getString("profession"));
			}

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}
		return info;
	}

	public void changeUserProfileInfo(Integer idUser, UserProfileInfo userProfile) throws Exception{
		Connection connection = dataSource.getConnection();
		Statement statement = null; 
		String query = "UPDATE userprofileinfo SET city='"+userProfile.getCity()+"', country='"+userProfile.getCountry()+"', profession='"+userProfile.getProfession()+"' WHERE idUser="+idUser;

		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			statement.execute(query);

		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}	
	}

	public void changeUserInfo(Integer idUser, String param, String value) throws Exception {
		Connection connection = dataSource.getConnection();
		Statement statement = null; 
		String query = "UPDATE users SET "+param+"='"+value+"' WHERE idUser="+idUser;
		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (statement != null) 
					statement.close();
				if (connection!= null)
					connection.close();
			} catch (SQLException e) {
				throw new Exception(e.getMessage());
			}
		}	
	}

}