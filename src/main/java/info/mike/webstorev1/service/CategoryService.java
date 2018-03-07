package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.domain.Category;

import java.util.Set;


public interface CategoryService {
    Set<Category> getAllCategories();
    Category findById(Long id);
    Set<CategoryCommand> getAllCategoriesCommand();
}
