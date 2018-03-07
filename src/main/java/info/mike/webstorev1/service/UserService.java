package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    UserCommand save(UserCommand userCommand);
}
