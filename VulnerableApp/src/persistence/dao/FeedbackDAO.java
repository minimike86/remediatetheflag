package persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

import model.UserFeedback;
import persistence.MysqlDataSource;

public class FeedbackDAO {

	private MysqlDataSource dataSource = new MysqlDataSource();
	private static Logger logger = LoggerFactory.getLogger(FeedbackDAO.class);

	public void removeFeedback(Integer idUser) throws Exception{
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = null; 
		String query = "DELETE FROM feedbacks WHERE idUser = ?";
		try {
			logger.debug("#SQL: "+query);
			statement = connection.prepareStatement(query);
			statement.setInt(1, idUser);
			statement.executeUpdate();
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

	public void storeNewFeedback(Integer idUser, String type, String message) throws Exception {

		Connection connection = dataSource.getConnection();
		Statement statement = null; 
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO feedbacks(idUser, area, msg, date) VALUES ("+idUser+", '"+type+"', '"+message+"', '"+date+"')";

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

	public List<UserFeedback> getFeedbacksFor(String whoFor) throws Exception{
		Connection connection = this.dataSource.getConnection();
		String query  = "SELECT feedbacks.idUser, area, msg, date FROM feedbacks JOIN users ON feedbacks.idUser=users.idUser where username='"+whoFor+"'";
		List<UserFeedback> list = new LinkedList<UserFeedback>();
		Statement statement = null;
		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			statement.execute(query);
			ResultSet result = statement.getResultSet();
			while(result.next()) {

				UserFeedback feed = new UserFeedback();
				feed.setIdUser(result.getInt("idUser"));
				feed.setMessage(result.getString("msg"));
				feed.setType(result.getString("area"));
				feed.setDate(result.getTimestamp("date"));
				list.add(feed);
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
		return list;
	}

	public List<UserFeedback> getFeedbacksForUser(Integer idUser) throws Exception{
		Connection connection = this.dataSource.getConnection();
		Statement statement = null; 
		String query  = "SELECT idUser, area, msg, date FROM feedbacks where idUser="+idUser;
		List<UserFeedback> list = new LinkedList<UserFeedback>();
		try {
			logger.debug("#SQL: "+query);

			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				UserFeedback feed = new UserFeedback();
				feed.setIdUser(result.getInt("idUser"));
				feed.setMessage(result.getString("msg"));
				feed.setType(result.getString("area"));
				feed.setDate(result.getTimestamp("date"));
				list.add(feed);
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
		return list;
	}

	public List<UserFeedback> getAllFeedbacks() throws Exception{
		Connection connection = this.dataSource.getConnection();
		Statement statement = null; 
		String query  = "SELECT idUser, area, msg, date FROM feedbacks where idUser>0";
		List<UserFeedback> list = new LinkedList<UserFeedback>();
		try {
			logger.debug("#SQL: "+query);
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				UserFeedback feed = new UserFeedback();
				feed.setIdUser(result.getInt("idUser"));
				feed.setMessage(result.getString("msg"));
				feed.setType(result.getString("area"));
				feed.setDate(result.getTimestamp("date"));
				list.add(feed);
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
		return list;
	}
}
