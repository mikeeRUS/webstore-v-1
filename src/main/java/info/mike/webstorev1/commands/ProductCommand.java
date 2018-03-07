package info.mike.webstorev1.commands;

import info.mike.webstorev1.domain.Category;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "categories")
@NoArgsConstructor
public class ProductCommand  {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    private String description;
    private BigDecimal price;
    private String manufacturer;
    private Long unitsInStock;
    private boolean discontinued;
    private Byte[] image;

    private Set<CategoryCommand> categories = new HashSet<>();

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

    public Set<CategoryCommand> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryCommand> categories) {
        this.categories = categories;
    }
}
