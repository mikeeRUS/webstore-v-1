package info.mike.webstorev1.service;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.repository.CartItemRepository;
import info.mike.webstorev1.repository.CartRepository;
import info.mike.webstorev1.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {

    public static final String SESSION_ID = "7CCB8B76D456BC1D6192BE19F35DF9FB";

    CartService cartService;

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartItemRepository cartItemRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cartService = new CartServiceImpl(cartRepository, productRepository, cartItemRepository);
    }

    @Test
    public void getAllCartItems() throws Exception {
        Cart cart = new Cart();
        cart.setCartSessionId(SESSION_ID);
        cart.getCartItemList().add(new CartItem());

        Optional<Cart> cartOptional = Optional.of(cart);

        when(cartRepository.findByCartSessionId(anyString())).thenReturn(cartOptional);

        Set<CartItem> allCartItems = cartService.getAllCartItems(SESSION_ID);
        assertFalse(allCartItems.isEmpty());
    }

    @Test
    public void addProductThatAlreadyExist() throws Exception {
        Product product = new Product();
        product.setId(1L);

        Cart cartTest = new Cart();
        cartTest.setSubTotal(new BigDecimal(9998));

        CartItem cartItemToAdd = new CartItem();
        cartItemToAdd.setQuantity(2);
        cartItemToAdd.setPrice(new BigDecimal(4999));
        cartItemToAdd.setProduct(product);

        Optional<Product> productOptional = Optional.of(product);

        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(cartItemToAdd);

        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        when(cartItemRepository.findByCart(any())).thenReturn(cartItemSet);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        cartService.addProduct(cartTest, 1L);

        verify(cartRepository, times(1)).save(argumentCaptor.capture());
        Cart cart = argumentCaptor.getValue();
        assertEquals(new Integer(3), cartItemSet.stream().findFirst().get().getQuantity());
        assertEquals(cartItemToAdd.getPrice().multiply(new BigDecimal(cartItemToAdd.getQuantity())), cart.getSubTotal());
    }

    @Test
    public void addProductThatNotExist() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal(4999));

        Cart cartTest = new Cart();
        cartTest.setSubTotal(new BigDecimal(0));

        Optional<Product> productOptional = Optional.of(product);

        Set<CartItem> cartItemSet = new HashSet<>();

        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        when(cartItemRepository.findByCart(any())).thenReturn(cartItemSet);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        cartService.addProduct(cartTest, 1L);

        verify(cartRepository, times(1)).save(argumentCaptor.capture());
        Cart savedCart = argumentCaptor.getValue();
        assertEquals(new Integer(1), savedCart.getCartItemList().stream().findFirst().get().getQuantity());
        assertEquals(product.getPrice(), savedCart.getSubTotal());

    }

    @Test
    public void deleteProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);

        Cart cartTest = new Cart();
        CartItem cartItemToAdd = new CartItem();

        cartItemToAdd.setQuantity(1);
        cartItemToAdd.setPrice(new BigDecimal(4999));
        cartItemToAdd.setProduct(product);

        cartTest.setSubTotal(new BigDecimal(4999));
        cartTest.getCartItemList().add(cartItemToAdd);

        Optional<Product> productOptional = Optional.of(product);
        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(cartItemToAdd);

        when(productRepository.findById(anyLong())).thenReturn(productOptional);
        when(cartItemRepository.findByCart(any())).thenReturn(cartItemSet);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        cartService.deleteProduct(cartTest, 1L);

        verify(cartRepository, times(1)).save(argumentCaptor.capture());
        Cart savedCart = argumentCaptor.getValue();
        assertEquals(new BigDecimal(0), savedCart.getSubTotal());
        assertTrue("Item has not been deleted", savedCart.getCartItemList().isEmpty());

    }

}