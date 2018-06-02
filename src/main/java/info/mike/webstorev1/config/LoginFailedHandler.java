package info.mike.webstorev1.config;

import info.mike.webstorev1.exceptions.UserNotActivatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {

    //@Autowired
    //private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        try {
            if (exception.getClass().isAssignableFrom(UserNotActivatedException.class)) {
                response.sendRedirect(request.getContextPath() + "/index");
                System.out.println("In Handler");
                //handlerExceptionResolver.resolveException(request, response, null, exception);
            } else {
                //response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (AuthenticationException exc) {
            System.out.println("Catched");

        }
    }



}
