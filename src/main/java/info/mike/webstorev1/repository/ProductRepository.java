package info.mike.webstorev1.repository;

import info.mike.webstorev1.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
