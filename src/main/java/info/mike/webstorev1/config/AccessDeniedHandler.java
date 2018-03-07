package info.mike.webstorev1.config;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("MikeLogger: User " + authentication.getName()
                    + " attempted to access the protected URL: "
            + httpServletRequest.getRequestURI());

            System.out.println("Context path: " + httpServletRequest.getContextPath());
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");

    }
}
