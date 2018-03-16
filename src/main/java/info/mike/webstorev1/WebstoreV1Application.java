package info.mike.webstorev1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication()
public class WebstoreV1Application {

	public static void main(String[] args) {
		SpringApplication.run(WebstoreV1Application.class, args);
	}
}
