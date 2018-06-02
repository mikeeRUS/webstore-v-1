package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserCommand saveNewUser(UserCommand userCommand);
}
