package info.mike.webstorev1.controllers;


import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.service.CartService;
import info.mike.webstorev1.service.ProductService;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CartRestControllerTest {

    public static final String SESSION_ID = "7CCB8B76D456BC1D6192BE19F35DF9FB";

    @Mock
    CartService cartService;

    @InjectMocks
    CartRestController cartRestController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(cartRestController)
                .build();
    }

    @Test
    public void showCartNotNull() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartSessionId(SESSION_ID);
        cart.setSubTotal(new BigDecimal(0));

        when(cartService.findByCartSessionId(any())).thenReturn(cart);

        mockMvc.perform(get("/rest/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.subTotal", equalTo(0)))
                .andExpect(jsonPath("$.cartSessionId", equalTo(SESSION_ID)));
    }

    @Test
    public void showCartNull() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartSessionId(SESSION_ID);
        cart.setSubTotal(new BigDecimal(0));

        when(cartService.findByCartSessionId(any())).thenReturn(null);
        when(cartService.createCart(anyString())).thenReturn(cart);

        mockMvc.perform(get("/rest/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.subTotal", equalTo(0)))
                .andExpect(jsonPath("$.cartSessionId", equalTo(SESSION_ID)));
    }

    @Test
    public void addProduct() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartSessionId(SESSION_ID);
        cart.setSubTotal(new BigDecimal(4999));

        CartItem cartItem = new CartItem(new Product());
        cartItem.setCart(cart);
        cart.getCartItemList().add(cartItem);

        when(cartService.addProduct(any(), anyLong())).thenReturn(cart);

        mockMvc.perform(put("/add/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.subTotal", equalTo(4999)))
                .andExpect(jsonPath("$.cartSessionId", equalTo(SESSION_ID)))
                .andExpect(jsonPath("$.cartItemList", is(not(empty()))));

        verify(cartService, times(1)).addProduct(any(), anyLong());
    }

    @Test
    public void deleteProduct() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setCartSessionId(SESSION_ID);
        cart.setSubTotal(new BigDecimal(0));

        when(cartService.findByCartSessionId(anyString())).thenReturn(cart);
        when(cartService.deleteProduct(any(), anyLong())).thenReturn(cart);

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.subTotal", equalTo(0)))
                .andExpect(jsonPath("$.cartSessionId", equalTo(SESSION_ID)))
                .andExpect(jsonPath("$.cartItemList", is(empty())));

        verify(cartService, times(1)).deleteProduct(any(), anyLong());
    }

}
