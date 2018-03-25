package info.mike.webstorev1.controllers;

import info.mike.webstorev1.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/cart")
    public String viewCart(Model model, HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getSession(true).getId();
        return "cart/viewCart";
    }
}
