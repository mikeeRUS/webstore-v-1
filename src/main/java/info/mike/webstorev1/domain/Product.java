package info.mike.webstorev1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@EqualsAndHashCode(exclude = {"categories", "cartItemList"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String manufacturer;
    private Long unitsInStock;
    private boolean discontinued;
    private Byte[] image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")//lazy
    @JsonIgnore
    private List<CartItem> cartItemList = new ArrayList<>();

    @ElementCollection
    private Map<String, String> productProperties = new HashMap<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Map<String, String> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(Map<String, String> productProperties) {
        this.productProperties = productProperties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Long unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
