package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.converters.UserCommandToUser;
import info.mike.webstorev1.converters.UserToUserCommand;
import info.mike.webstorev1.domain.Role;
import info.mike.webstorev1.domain.User;
import info.mike.webstorev1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserCommandToUser userCommandToUser;
    private UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User not found !");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserCommand save(UserCommand userCommand){
        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = userRepository.save(detachedUser);
        return userToUserCommand.convert(savedUser);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
