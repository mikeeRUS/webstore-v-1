package info.mike.webstorev1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication()
public class WebstoreV1Application {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:testdb"; //jdbc:h2:mem:testdb
	private static final String DB_USER = "Sa";
	private static final String DB_PASSWORD = "";

	public static void main(String[] args) {
		SpringApplication.run(WebstoreV1Application.class, args);
		try {
			Connection dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			dbConnection.prepareStatement( "INSERT INTO user (id, first_name, last_name, email, password) VALUES (1, 'Memory', 'Not', 'info@wp.pl', 'test')").execute();
			dbConnection.prepareStatement("INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN')").execute();
			dbConnection.prepareStatement("INSERT INTO users_roles (user_id, role_id) VALUES (1, 1)").execute();
		} catch (SQLException exc) {
			System.out.println(exc);
		}
	}
}
