package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    final Long ID = new Long(1L);
    final String DESC = "Test description";

    CategoryToCategoryCommand categoryToCategoryCommand;

    @Before
    public void setUp() throws Exception {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void convert() throws Exception {
        Category category = new Category();
        category.setId(ID);
        category.setCategoryName(DESC);

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESC, categoryCommand.getCategoryName());
    }

}
