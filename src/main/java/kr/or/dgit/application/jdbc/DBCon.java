package kr.or.dgit.application.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBCon {
	private static final DBCon instance = new DBCon(); // singletone pattern

	private Connection connection;
	private Properties properties;

	public static DBCon getInstance() {
		return instance;
	}

	private DBCon() {
		try {
			LoadProperties pro = new LoadProperties();
			properties = pro.getProperties();
			System.out.println(properties+"dfdfadf");
			connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		} catch (SQLException e) {
			System.err.printf("%s = %s%n", e.getMessage(), e.getErrorCode());
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
	public Properties getProperties() {
		return properties;
	}
	
	public void connectionClose() {
		try {
			connection.close();
			JOptionPane.showMessageDialog(null, "DB연결 해제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
