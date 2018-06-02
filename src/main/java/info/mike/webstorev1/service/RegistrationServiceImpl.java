package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private JavaMailSender javaMailSender;

    public RegistrationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendActivationEmailToUser(UserCommand userCommand) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userCommand.getEmail());
        simpleMailMessage.setFrom("mr.hektor@wp.pl");
        simpleMailMessage.setSubject("Hello");
        simpleMailMessage.setText(userCommand.getActivationKey());

        javaMailSender.send(simpleMailMessage);

    }
}
