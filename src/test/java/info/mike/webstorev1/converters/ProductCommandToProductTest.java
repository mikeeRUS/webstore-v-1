package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductCommandToProductTest {

    final Long ID = 1L;
    final String NAME = "Test Name";
    final String DESC = "Test Description";
    final Boolean DISCONTINUED = false;
    final Long UNITSINSTOCK = 2L;
    final BigDecimal PRICE = new BigDecimal(49);
    final String MANUFACTURER = "Test Manufacturer";
    final Byte[] IMAGE = new Byte[999];

    ProductCommandToProduct productCommandToProduct;

    @Before
    public void setUp() throws Exception {
        productCommandToProduct = new ProductCommandToProduct(new CategoryCommandToCategory());
    }

    @Test
    public void convert() throws Exception {
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(ID);
        productCommand.setName(NAME);
        productCommand.setDescription(DESC);
        productCommand.setDiscontinued(DISCONTINUED);
        productCommand.setUnitsInStock(UNITSINSTOCK);
        productCommand.setPrice(PRICE);
        productCommand.setManufacturer(MANUFACTURER);
        productCommand.setImage(IMAGE);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setCategoryName("Test Category");

        productCommand.getCategories().add(categoryCommand);

        Product product = productCommandToProduct.convert(productCommand);

        assertEquals(ID, product.getId());
        assertEquals(NAME, product.getName());
        assertEquals(DESC, product.getDescription());
        assertEquals(DISCONTINUED, product.isDiscontinued());
        assertEquals(PRICE, product.getPrice());
        assertEquals(MANUFACTURER, product.getManufacturer());
        assertEquals(1, product.getCategories().size());
        assertArrayEquals(IMAGE, product.getImage());
    }

}
