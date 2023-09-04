package com.atm.whxismou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/***
 * 
 * @author Whxismou Clase encargada de ejercer la conexion a la base de datos
 *
 */
public class ConexionBBDD {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bankatm";
	private static final String JDBC_USER = "root2";
	private static final String JDBC_PASSWORD = "";

	public void connect() throws SQLException {
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

		PreparedStatement state = cn.prepareStatement("");

		ResultSet rs = state.executeQuery();

	}

	public boolean checkUserExist(String user) throws SQLException {
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

		PreparedStatement state = cn.prepareStatement("SELECT * FROM users WHERE username=?");
		state.setString(1, user);

		ResultSet rs = state.executeQuery();

		if (rs.next()) {
			rs.close();
			return true;
		} else {
			rs.close();
			return false;
		}
	}

	public int introduce(String username, String passEncrypted) throws SQLException {
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		int id = 0;
		PreparedStatement state = cn.prepareStatement("INSERT INTO bankatm.users (username, password) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
		state.setString(1, username);
		state.setString(2, passEncrypted);
		state.executeUpdate();
		
		ResultSet rs = state.getGeneratedKeys();
		if(rs.next()) {
			id = rs.getInt(1);			
		}
		
		return id;
	}

	public User getUserDB(String usernameIntroduced) throws SQLException {
		User usuario = null;
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		PreparedStatement state = cn.prepareStatement("SELECT * FROM users WHERE username = ?");
		 
		state.setString(1, usernameIntroduced);
		
		ResultSet rs = state.executeQuery();
		
		if(rs.next()){
			int id = rs.getInt("id");
			String passwordStored = rs.getString("password");
			
			usuario = new User(id, usernameIntroduced, passwordStored);
		}
		
		return usuario;
	}

	public int checkBalance(User user) throws SQLException {
		int currentBalance = 0;
		
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		PreparedStatement state = cn.prepareStatement("SELECT current_balance FROM transactions WHERE user_id = ?");
		 
		state.setInt(1, user.getId());
		
		ResultSet rs = state.executeQuery();
		
		if(rs.next()) {
			
			currentBalance = rs.getInt("current_balance");
			
			return currentBalance;
		}else {
			return 0;			
		}
		
	}

}
