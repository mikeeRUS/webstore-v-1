package info.mike.webstorev1.controllers;

import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.exceptions.NotFoundException;
import info.mike.webstorev1.service.CategoryService;
import info.mike.webstorev1.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping("/product/view/{id}")
    public String viewProduct(@PathVariable String id, Model model) {
        model.addAttribute("cat", categoryService.getAllCategories());
        model.addAttribute("product", productService.findById(Long.valueOf(id)));
        return "product/viewProduct";
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("cat", categoryService.getAllCategoriesCommand());
        model.addAttribute("productf", new ProductCommand());
        return "product/addProduct";
    }

    @RequestMapping("/product/edit/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        model.addAttribute("cat", categoryService.getAllCategoriesCommand());
        model.addAttribute("productf", productService.findCommandById(Long.valueOf(id)));//changed
        return "product/addProduct";
    }

    @RequestMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id, Model model) {
       productService.deleteProduct(Long.valueOf(id));
        return "redirect:/product/list";
    }

    @PostMapping("product")
    public String saveNewProduct(@Valid @ModelAttribute("productf") ProductCommand productCommand, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return "redirect:/product/new";
        }
        log.debug("Field value: " + bindingResult.getFieldValue("categories"));
        log.debug("Field type: " + bindingResult.getFieldType("categories"));
        log.debug("Raw field value: " + bindingResult.getRawFieldValue("categories"));

        productService.saveCommand(productCommand);
        return "redirect:/product/list";
    }

    @RequestMapping("/product/list")
    public String listProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/list";

    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exceptions/notFound");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
