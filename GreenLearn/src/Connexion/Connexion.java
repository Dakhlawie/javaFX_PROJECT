package Connexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
	private static Connection connection;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/GreenLearn?characterEncoding=UTF-8","root","");
			System.out.println("connected suucessuful");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
