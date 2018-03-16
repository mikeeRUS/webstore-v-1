package info.mike.webstorev1.controllers;

import info.mike.webstorev1.commands.UserCommand;
import info.mike.webstorev1.domain.User;
import info.mike.webstorev1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserCommand());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserCommand userCommand,
                                      BindingResult bindingResult){
        User existing = userService.findByEmail(userCommand.getEmail());

        if (existing != null){
            bindingResult.rejectValue("email", null, "Podany email już istnieje.");
        }

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return "/registration";
        }

        userService.save(userCommand);
        return "redirect:/index";
    }


}
