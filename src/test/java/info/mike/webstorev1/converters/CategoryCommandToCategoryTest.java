package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    final Long ID = new Long(1L);
    final String DESC = "Test description";

    CategoryCommandToCategory categoryCommandToCategory;

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void convert() throws Exception {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setCategoryName(DESC);

        Category category = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(ID, category.getId());
        assertEquals(DESC, category.getCategoryName());

    }

}
