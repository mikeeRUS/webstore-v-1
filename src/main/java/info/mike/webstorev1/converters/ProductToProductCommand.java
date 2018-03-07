package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public ProductToProductCommand(CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public ProductCommand convert(Product source) {
        if (source == null){
            return null;
        }

        final ProductCommand productCommand = new ProductCommand();
        productCommand.setId(source.getId());
        productCommand.setDescription(source.getDescription());
        productCommand.setDiscontinued(source.isDiscontinued());
        productCommand.setManufacturer(source.getManufacturer());
        productCommand.setPrice(source.getPrice());
        productCommand.setUnitsInStock(source.getUnitsInStock());
        productCommand.setImage(source.getImage());
        productCommand.setName(source.getName());



        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Category category) -> productCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }



        return productCommand;
    }
}
