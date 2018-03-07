package info.mike.webstorev1.repository;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    Optional<CartItem> findByProduct(Product productId);
    Set<CartItem> findAllByProduct(Product product);
    Set<CartItem> findByCart(Cart cart);
}
