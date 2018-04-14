package info.mike.webstorev1.controllers;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.exceptions.NotFoundException;
import info.mike.webstorev1.service.CategoryService;
import info.mike.webstorev1.service.ProductService;
import info.mike.webstorev1.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    ProductService productService;

    MockMvc mockMvc;

    @InjectMocks
    ProductController productController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void viewProduct() throws Exception {
        Set<Category> categorySet = new HashSet<>();
        Category category = new Category();
        category.setId(1L);
        categorySet.add(category);

        Product product = new Product();
        product.setId(1L);

        when(categoryService.getAllCategories()).thenReturn(categorySet);
        when(productService.findById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/product/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/viewProduct"))
                .andExpect(model().attributeExists("cat"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void newProduct() throws Exception {
        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommandSet.add(categoryCommand);

        when(categoryService.getAllCategoriesCommand()).thenReturn(categoryCommandSet);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/addProduct"))
                .andExpect(model().attributeExists("cat"))
                .andExpect(model().attributeExists("productf"));
    }

    @Test
    public void editProduct() throws Exception {
        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommandSet.add(categoryCommand);

        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);

        when(categoryService.getAllCategoriesCommand()).thenReturn(categoryCommandSet);
        when(productService.findCommandById(anyLong())).thenReturn(productCommand);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/addProduct"))
                .andExpect(model().attributeExists("cat"))
                .andExpect(model().attributeExists("productf"));
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));

        verify(productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    public void saveNewProduct() throws Exception {
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);

        when(productService.saveCommand(any())).thenReturn(productCommand);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("name", "product")
                .param("description", "shortDescription"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));
    }

    @Test
    public void saveNewProductFail() throws Exception {
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);

        when(productService.saveCommand(any())).thenReturn(productCommand);

        mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("name", "")
                .param("description", "shortDescription"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/new"));
    }

    @Test
    public void listProduct() throws Exception {
        Set<Product> productSet = new HashSet<>();
        Product product = new Product();
        product.setId(1L);
        productSet.add(product);

        when(productService.getAllProducts()).thenReturn(productSet);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/list"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void handleNotFoundException() throws Exception {
        when(productService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/product/view/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("exceptions/notFound"));
    }

}
