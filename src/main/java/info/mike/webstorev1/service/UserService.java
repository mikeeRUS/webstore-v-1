package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserCommand save(UserCommand userCommand);
}
