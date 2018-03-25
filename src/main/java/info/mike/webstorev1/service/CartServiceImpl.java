package info.mike.webstorev1.service;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.repository.CartItemRepository;
import info.mike.webstorev1.repository.CartRepository;
import info.mike.webstorev1.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    ProductRepository productRepository;
    CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Set<CartItem> getAllCartItems(String cartSessionId) {//poprawic
        Set<CartItem> allCartItems = new HashSet<>();
        Cart cart = cartRepository.findByCartSessionId(cartSessionId).get();
        cart.getCartItemList().stream().forEach(allCartItems::add);
        return allCartItems;
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart createCart(String cartSessionId) {
        Cart cart = new Cart();
        cart.setCartSessionId(cartSessionId);
        cart.setSubTotal(new BigDecimal(0));
        Cart savedCart = cartRepository.save(cart);
        return savedCart;
    }

    @Override
    public Cart findByCartSessionId(String cartSessionId) {
        Optional<Cart> cartBySessionIdOptional = cartRepository.findByCartSessionId(cartSessionId);

        if(cartBySessionIdOptional.isPresent()) {
            return cartBySessionIdOptional.get();
        } else
            return null;
    }


    @Override
    public Cart addProduct(Cart cart, Long productId) {
        Product productToCart = productRepository.findById(productId).get();
        boolean isPresent = false;

        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemRepository.findByCart(cart).stream().forEach(cartItemSet::add); //get all products in cart

        for (CartItem cartItem : cartItemSet) { //check if product to add already exists in cart, if positive - increase quantity
            if(cartItem.getProduct().equals(productToCart)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cart.setSubTotal(cart.getSubTotal().add(cartItem.getPrice()));
                isPresent = true;
            }
        }

        if (!isPresent || cartItemSet.isEmpty()) {//if negative create new cart item
            CartItem cartItemToAdd = new CartItem(productToCart);
            cartItemToAdd.setCart(cart);
            cart.getCartItemList().add(cartItemToAdd);
            cart.setSubTotal(cart.getSubTotal().add(cartItemToAdd.getPrice()));
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteProduct(Cart cart, Long productId) {
        Product productToCart = productRepository.findById(productId).get();

        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemRepository.findByCart(cart).stream().forEach(cartItemSet::add); //get all products in cart

        for (CartItem cartItem : cartItemSet) { //check if product to add already exists in cart, if positive - increase quantity
            if(cartItem.getProduct().equals(productToCart)) {
                cart.setSubTotal(cart.getSubTotal().subtract(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())))); //quantity * price
                cart.getCartItemList().remove(cartItem);
                cartItemRepository.delete(cartItem);
            }
        }
        return cartRepository.save(cart);
    }
}
