package info.mike.webstorev1.controllers;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.Role;
import info.mike.webstorev1.repository.UserRepository;
import info.mike.webstorev1.service.RegistrationService;
import info.mike.webstorev1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    //@Mock
    @Autowired
    UserService userService;

    //@Mock
    @Autowired
    UserRepository userRepository;

    //@Mock
    @Autowired
    RegistrationService registrationService;

    UserController userController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        userController = new UserController(userService, userRepository, registrationService);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        UserCommand user = new UserCommand();
        user.setEmail("krzy4@wp.pl");
        user.setPassword("Motor123");
        userService.saveNewUser(user);
    }

    @Test
    public void handleNotFoundException() throws Exception {
        mockMvc.perform(formLogin("/login")
            .user("krzy4@wp.pl")
            .password("Motor123")).andDo(print());
    }

}
