package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.converters.ProductCommandToProduct;
import info.mike.webstorev1.converters.ProductToProductCommand;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.exceptions.NotFoundException;
import info.mike.webstorev1.repository.CategoryRepository;
import info.mike.webstorev1.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCommandToProduct productCommandToProduct;
    private final ProductToProductCommand productToProductCommand;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductCommandToProduct productCommandToProduct, ProductToProductCommand productToProductCommand) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCommandToProduct = productCommandToProduct;
        this.productToProductCommand = productToProductCommand;
    }

    @Override
    public Set<Product> getAllProducts() {
        Set<Product> productsSet = new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(productsSet::add);
        return productsSet;
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(!productOptional.isPresent()) {
            throw new NotFoundException();
        }
        return productOptional.get();
    }

    @Override
    public Set<Product> findByCategory(Long id) {
        Set<Product> productsByCategory = new HashSet<>();
        getAllProducts().stream().filter(c -> c.getCategories().contains(categoryRepository.findById(id).get()))
                .forEach(productsByCategory::add);
        return productsByCategory;
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Transactional
    @Override
    public ProductCommand saveCommand(ProductCommand productCommand) {
        Product detachedProduct = productCommandToProduct.convert(productCommand);
        Product savedProduct = productRepository.save(detachedProduct);
        return productToProductCommand.convert(savedProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
