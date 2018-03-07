package info.mike.webstorev1.repository;

import info.mike.webstorev1.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
