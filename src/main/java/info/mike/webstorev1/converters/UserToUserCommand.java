package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    PasswordEncoder passwordEncoder;

    public UserToUserCommand(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Nullable
    @Override
    public UserCommand convert(User source) {
        if(source == null) {
            return null;
        }
        final UserCommand userCommand = new UserCommand();
        userCommand.setFirstName(source.getFirstName());
        userCommand.setLastName(source.getLastName());
        userCommand.setEmail(source.getEmail());
        userCommand.setPassword(passwordEncoder.encode(source.getPassword()));
        return userCommand;
    }
}
