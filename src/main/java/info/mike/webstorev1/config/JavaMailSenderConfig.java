package info.mike.webstorev1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class JavaMailSenderConfig {

    private final String PASSWORD = "PA$$M4P@cpu";
    private final String USERNAME = "mr.hektor@wp.pl";
    private final String HOST = "smtp.wp.pl";
    private final int PORT = 465;

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();

        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        javaMailSender.setHost(HOST);
        javaMailSender.setPort(PORT);
        javaMailSender.setPassword(PASSWORD);
        javaMailSender.setUsername(USERNAME);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

}
