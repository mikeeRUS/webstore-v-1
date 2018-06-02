package info.mike.webstorev1.controllers;

import info.mike.webstorev1.exceptions.UserNotActivatedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class ExceptionHandlerController extends SimpleUrlAuthenticationFailureHandler {

    //ToDo
    public ModelAndView handleProductNotFoundException(Exception exception) {
        return new ModelAndView();
    }

    //@ExceptionHandler(UserNotActivatedException.class)
    public ModelAndView handleNotFoundException(UserNotActivatedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
