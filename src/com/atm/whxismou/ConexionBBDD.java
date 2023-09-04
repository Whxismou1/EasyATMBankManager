package com.atm.whxismou;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/***
 * 
 * @author Whxismou Clase encargada de ejercer la conexion a la base de datos
 *
 */
public class ConexionBBDD {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bankatm";
	private static final String JDBC_USER = "root2";
	private static final String JDBC_PASSWORD = "";

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
		PreparedStatement state = cn.prepareStatement("INSERT INTO bankatm.users (username, password) VALUES(?,?)",
				Statement.RETURN_GENERATED_KEYS);
		state.setString(1, username);
		state.setString(2, passEncrypted);
		state.executeUpdate();

		ResultSet rs = state.getGeneratedKeys();
		if (rs.next()) {
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

		if (rs.next()) {
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

		if (rs.next()) {

			currentBalance = rs.getInt("current_balance");

			return currentBalance;
		} else {
			return 0;
		}

	}

	public List<String> getTransactionList(User user) throws SQLException {
		List<String> transactionList = new ArrayList<>();

		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		PreparedStatement state = cn.prepareStatement("SELECT transaction_data FROM transactions WHERE user_id = ?");

		state.setInt(1, user.getId());

		ResultSet rs = state.executeQuery();

		if (rs.next()) {
			String jsonList = rs.getString("transaction_data");

			// Utiliza Gson para deserializar la cadena JSON en una lista de cadenas
			Gson gson = new Gson();
			Type listType = new TypeToken<List<String>>() {
			}.getType();
			transactionList = gson.fromJson(jsonList, listType);

			return transactionList;
		} else {
			return transactionList;
		}
	}

	public void updateBalance(User user, int nuevoBalance) throws SQLException {
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

		PreparedStatement state = cn.prepareStatement("UPDATE transactions SET current_balance=? WHERE user_id=?");

		state.setInt(1, nuevoBalance);
		state.setInt(2, user.getId());

		int nElem = state.executeUpdate();
		if (nElem == 0) {
			System.out.println("Nothing changed!");
		} else {
			System.out.println("Balance actualizado correctamente!");
		}
	}

	public void updateTransactions(User user, List<String> transactionList) throws SQLException {

		Gson gson = new Gson();
		String transactionsString = gson.toJson(transactionList);
		
		Connection cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

		PreparedStatement state = cn.prepareStatement("UPDATE transactions SET transaction_data=? WHERE user_id=?");

		state.setString(1, transactionsString);
		state.setInt(2, user.getId());

		int nElem = state.executeUpdate();
		if (nElem == 0) {
			System.out.println("Nothing changed!");
		} else {
			System.out.println("Transacciones actualizadas correctamente!");
		}
	}

}
