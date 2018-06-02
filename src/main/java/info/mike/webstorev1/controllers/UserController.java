package info.mike.webstorev1.controllers;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.exceptions.UserNotActivatedException;
import info.mike.webstorev1.repository.UserRepository;
import info.mike.webstorev1.service.RegistrationService;
import info.mike.webstorev1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    public UserController(UserService userService, UserRepository userRepository, RegistrationService registrationService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model, HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getUserPrincipal() != null)
            return "redirect:/index";
        else {
            model.addAttribute("user", new UserCommand());
            return "registration";
        }
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserCommand userCommand,
                                      BindingResult bindingResult){

        userRepository.findByEmail(userCommand.getEmail()).ifPresent(u -> {
            bindingResult.rejectValue("email", null, "Podany email już istnieje.");
            });

        if(bindingResult.hasErrors()) {
            return "/registration";
        }
        UserCommand savedUser = userService.saveNewUser(userCommand);
        //registrationService.sendActivationEmailToUser(savedUser);
        return "redirect:/index";
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ModelAndView handleNotFoundException(UserNotActivatedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("custom");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }



}
