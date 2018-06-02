package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.converters.UserCommandToUser;
import info.mike.webstorev1.converters.UserToUserCommand;
import info.mike.webstorev1.domain.User;
import info.mike.webstorev1.exceptions.UserNotActivatedException;
import info.mike.webstorev1.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserCommandToUser userCommandToUser;
    private UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(u -> createUser(u))
            .orElseThrow(() -> new UsernameNotFoundException("User " + email +  " not found !"));
    }

    @Override
    public UserCommand saveNewUser(UserCommand userCommand){
        userCommand.setActive(false);
        userCommand.setActivationKey(UUID.randomUUID().toString());

        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = userRepository.save(detachedUser);
        return userToUserCommand.convert(savedUser);
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        if(!user.isActive()) {
            throw new UserNotActivatedException(":<");
        }
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(),
            authorities);
    }
}
