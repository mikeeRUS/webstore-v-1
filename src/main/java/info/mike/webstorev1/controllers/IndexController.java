package info.mike.webstorev1.controllers;

import info.mike.webstorev1.exceptions.UserNotActivatedException;
import info.mike.webstorev1.service.CategoryService;
import info.mike.webstorev1.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class IndexController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public IndexController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model, HttpServletRequest httpServletRequest)
    {
        model.addAttribute("cat", categoryService.getAllCategories());
        log.debug("Context path: " + httpServletRequest.getContextPath());
        log.debug("Session: " + httpServletRequest.getSession());
        log.debug("Session ID: " + httpServletRequest.getSession().getId());
        log.debug("Remote User: " + httpServletRequest.getRemoteUser());
        return "index";
    }

    @RequestMapping("/category/{id}")
    public String getCategoryPage(@PathVariable String id, Model model) {
        model.addAttribute("cat", categoryService.getAllCategories());
        model.addAttribute("products", productService.findByCategory(Long.valueOf(id)));
        model.addAttribute("actualcategory", Long.valueOf(id));
        return "category";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getUserPrincipal() != null)
        return "redirect:/index";
        else
            return "login";
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ModelAndView handleNotFoundException(UserNotActivatedException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }


    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/index";
    }
}
