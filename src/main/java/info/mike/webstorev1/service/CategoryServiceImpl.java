package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.CategoryCommand;
import info.mike.webstorev1.converters.CategoryToCategoryCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<Category> getAllCategories() {
        Set<Category> categoriesSet = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categoriesSet::add);
        return categoriesSet;
    }

    @Override
    public Set<CategoryCommand> getAllCategoriesCommand() {
        Set<Category> categoriesSet = new HashSet<>();
        Set<CategoryCommand> categoriesCommandSet = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categoriesSet::add);

        categoriesSet.stream().map(c -> categoryToCategoryCommand.convert(c)).forEach(categoriesCommandSet::add);
        return categoriesCommandSet;
    }

    @Override
    public Category findById(Long id) {
        //ToDo
        return categoryRepository.findById(id).get();
    }
}
