package info.mike.webstorev1.service;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;

import java.util.Set;

public interface CartService {

    Cart saveCart(Cart cart);
    Cart createCart(String cartSessionId);
    Cart findByCartSessionId(String cartSessionId);
    Cart addProduct(Cart cart, Long productId);
    Cart deleteProduct(Cart cart, Long productId);
    Set<CartItem> getAllCartItems(String cartSessionId);
}
