package info.mike.webstorev1.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    //ToDo
    public ModelAndView handleProductNotFoundException(Exception exception) {
        return new ModelAndView();
    }

}
