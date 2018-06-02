package info.mike.webstorev1.service;

import info.mike.webstorev1.WebstoreV1Application;
import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.User;
import info.mike.webstorev1.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebstoreV1Application.class)
@Transactional
public class UserServiceImplTest {

    private final String EMAIL = "test@test.pl";

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        UserCommand user = new UserCommand();
        user.setEmail(EMAIL);
        user.setPassword("pass");
        userService.saveNewUser(user);
    }

    @Test
    @Transactional
    public void assertThatUserCanBeFoundByEmail() throws Exception {
        UserDetails userDetails = userService.loadUserByUsername(EMAIL);
        assertNotNull(userDetails);
        assertThat(userDetails.getUsername()).isEqualTo(EMAIL);
    }

    @Test
    public void save() throws Exception {
        //ToDo
    }

}
