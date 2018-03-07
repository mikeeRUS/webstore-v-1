package info.mike.webstorev1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@EqualsAndHashCode
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//lazy
    //@JoinColumn(name = "idcart")
    @JoinTable(name = "cart_cartitem",
            joinColumns = @JoinColumn(name = "cartitem_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne()//lazy
    //@JoinColumn(name = "idproduct")
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    public CartItem() {
        super();
    }

    public CartItem(Product product) {
        this.quantity = 1;
        this.product = product;
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
