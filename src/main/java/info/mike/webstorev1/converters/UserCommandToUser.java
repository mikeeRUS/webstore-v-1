package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.Role;
import info.mike.webstorev1.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    PasswordEncoder passwordEncoder;

    public UserCommandToUser(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Nullable
    @Override
    public User convert(UserCommand source) {
        if(source == null)
        {
            return null;
        }
        final User user = new User();
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setEmail(source.getEmail());
        user.setPassword(passwordEncoder.encode(source.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return user;
    }
}
