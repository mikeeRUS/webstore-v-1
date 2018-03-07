package info.mike.webstorev1.service;


import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Product;

import java.util.Set;

public interface ProductService {

    Set<Product> getAllProducts();
    Product findById(Long id);
    Set<Product> findByCategory(Long id);
    Product saveProduct(Product product);
    ProductCommand saveCommand(ProductCommand productCommand);
    void deleteProduct(Long id);
}
