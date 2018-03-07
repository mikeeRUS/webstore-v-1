package info.mike.webstorev1.controllers;

import info.mike.webstorev1.service.CategoryService;
import info.mike.webstorev1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
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
        System.out.println("Context path: " + httpServletRequest.getContextPath());
        System.out.println("Session: " + httpServletRequest.getSession());
        System.out.println("Session ID: " + httpServletRequest.getSession().getId());
        System.out.println("Remote User: " + httpServletRequest.getRemoteUser());
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

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:/index";
    }
}
