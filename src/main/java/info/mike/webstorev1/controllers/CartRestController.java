package info.mike.webstorev1.controllers;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.repository.CartRepository;
import info.mike.webstorev1.service.CartService;
import info.mike.webstorev1.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class CartRestController {

    CartService cartService;
    ProductService productService;

    public CartRestController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/addcart")
    public void createCart(HttpServletRequest httpServletRequest) {
        cartService.createCart(httpServletRequest.getSession(true).getId());
        System.out.println("Utworzono koszyk(GET)");
    }

    @GetMapping("/rest/cart")
    public ResponseEntity<Cart> showCart(HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getSession(true).getId();
        Cart cartBySessionId = cartService.findByCartSessionId(sessionId);
        if (cartBySessionId == null) {
            return new ResponseEntity<Cart>(cartService.createCart(sessionId), HttpStatus.OK);
        }
        return new ResponseEntity<Cart>(cartBySessionId, HttpStatus.OK);
    }

    @PutMapping("/add/{productId}")
    public ResponseEntity<Cart> addProduct(@PathVariable String productId, HttpServletRequest httpServletRequest) {
        Cart cartBySessionId = cartService.findByCartSessionId(httpServletRequest.getSession(true).getId());

        if (cartBySessionId == null) {
            cartBySessionId = cartService.createCart(httpServletRequest.getSession(true).getId());
        }
        Cart savedCart = cartService.addProduct(cartBySessionId, Long.valueOf(productId));
        System.out.println("Wywołano addProduct(PUT)");
        return new ResponseEntity<Cart>(savedCart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Cart> deleteProduct(@PathVariable String productId, HttpServletRequest httpServletRequest) {
        Cart cartBySessionId = cartService.findByCartSessionId(httpServletRequest.getSession(true).getId());

        if (cartBySessionId == null) {
            //throw new CartNotFoundException();
            return null;
        }
            Cart savedCart = cartService.deleteProduct(cartBySessionId, Long.valueOf(productId));
            System.out.println("Wywołano deleteProduct(DELETE)");

        return new ResponseEntity<Cart>(savedCart, HttpStatus.OK);
    }



    @GetMapping("/rest")
    @CrossOrigin
    public ResponseEntity<Cart> get(HttpServletRequest httpServletRequest) {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartSessionId(httpServletRequest.getSession(true).getId());
        cart.setSubTotal(new BigDecimal(20));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setPrice(new BigDecimal(20));
        cartItem.setProduct(new Product());
        cart.getCartItemList().add(cartItem);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @GetMapping("/resttest")
    @CrossOrigin
    public ResponseEntity<Cart> getTest(HttpServletRequest httpServletRequest) {
        Cart cart = cartService.findByCartSessionId(httpServletRequest.getSession(true).getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }
}
