package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StringToCCConverter implements Converter<String, CategoryCommand> {

    CategoryService categoryService;
    CategoryToCategoryCommand categoryToCategoryCommand;

    public StringToCCConverter(CategoryService categoryService, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryService = categoryService;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Nullable
    @Override
    public CategoryCommand convert(String source) {
        return categoryToCategoryCommand.convert(categoryService.findById(Long.valueOf(source)));
    }
}
