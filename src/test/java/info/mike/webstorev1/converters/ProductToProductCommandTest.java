package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductToProductCommandTest {

    final Long ID = 1L;
    final String NAME = "Test Name";
    final String DESC = "Test Description";
    final Boolean DISCONTINUED = false;
    final Long UNITSINSTOCK = 2L;
    final BigDecimal PRICE = new BigDecimal(49);
    final String MANUFACTURER = "Test Manufacturer";
    final Byte[] IMAGE = new Byte[999];

    ProductToProductCommand productToProductCommand;

    @Before
    public void setUp() throws Exception {
        productToProductCommand = new ProductToProductCommand(new CategoryToCategoryCommand());
    }

    @Test
    public void convert() throws Exception {
        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setDescription(DESC);
        product.setDiscontinued(DISCONTINUED);
        product.setUnitsInStock(UNITSINSTOCK);
        product.setPrice(PRICE);
        product.setManufacturer(MANUFACTURER);
        product.setImage(IMAGE);

        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test Category");

        product.getCategories().add(category);

        ProductCommand productCommand = productToProductCommand.convert(product);

        assertEquals(ID, productCommand.getId());
        assertEquals(NAME, productCommand.getName());
        assertEquals(DESC, productCommand.getDescription());
        assertEquals(DISCONTINUED, productCommand.isDiscontinued());
        assertEquals(PRICE, productCommand.getPrice());
        assertEquals(MANUFACTURER, productCommand.getManufacturer());
        assertEquals(1, productCommand.getCategories().size());
        assertArrayEquals(IMAGE, productCommand.getImage());
    }

}
