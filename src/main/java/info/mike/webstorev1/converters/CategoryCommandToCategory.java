package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.service.CategoryService;
import info.mike.webstorev1.service.ProductService;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {


    CategoryService categoryService;

    public CategoryCommandToCategory( CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryName(source.getCategoryName());
        categoryService.findById(source.getId()).getProducts().stream().forEach(category.getProducts()::add);
        return category;
    }
}
