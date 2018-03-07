package info.mike.webstorev1.repository;

import info.mike.webstorev1.domain.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long>{

    Optional<Cart> findByCartSessionId(String cartSessionId);
}
