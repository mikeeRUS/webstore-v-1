package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.converters.ProductCommandToProduct;
import info.mike.webstorev1.converters.ProductToProductCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.repository.CategoryRepository;
import info.mike.webstorev1.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProductCommandToProduct productCommandToProduct;

    @Mock
    ProductToProductCommand productToProductCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository
                , categoryRepository
                , productCommandToProduct
                , productToProductCommand);
    }

    @Test
    public void findById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        Optional<Product> productOptional= Optional.of(product);

        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        Product returnedProduct = productService.findById(1L);
        assertNotNull("Null returned", returnedProduct);
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findByCategory() throws Exception {
        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        product.getCategories().add(category);

        Set<Product> products = new HashSet<>();
        products.add(product);

        Optional<Category> categoryOptional = Optional.of(category);

        when(productRepository.findAll()).thenReturn(products);
        when(categoryRepository.findById(anyLong())).thenReturn(categoryOptional);

        Set<Product> productsByCategorySet = productService.findByCategory(1L);

        assertFalse(productsByCategorySet.isEmpty());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteProduct() throws Exception {
        productService.deleteProduct(2L);

        verify(productRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findCommandById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        Optional<Product> productOptional= Optional.of(product);

        when(productRepository.findById(anyLong())).thenReturn(productOptional);

        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(1L);

        when(productToProductCommand.convert(any())).thenReturn(productCommand);

        ProductCommand productCommandById = productService.findCommandById(1L);

        assertNotNull("Null returned", productCommandById);
        verify(productRepository, times(1)).findById(anyLong());
    }

}