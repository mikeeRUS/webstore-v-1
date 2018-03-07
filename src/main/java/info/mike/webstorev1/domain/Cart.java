package info.mike.webstorev1.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subTotal;
    private String cartSessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)//lazy
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<CartItem> cartItemList = new ArrayList<>();

    public Cart() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getCartSessionId() {
        return cartSessionId;
    }

    public void setCartSessionId(String cartSessionId) {
        this.cartSessionId = cartSessionId;
    }


}
