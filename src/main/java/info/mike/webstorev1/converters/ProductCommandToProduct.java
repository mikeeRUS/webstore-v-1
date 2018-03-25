package info.mike.webstorev1.converters;

import info.mike.webstorev1.commands.ProductCommand;
import info.mike.webstorev1.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandToProduct implements Converter<ProductCommand, Product> {

    CategoryCommandToCategory categoryCommandToCategory;

    public ProductCommandToProduct(CategoryCommandToCategory categoryCommandToCategory) {
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Product convert(ProductCommand source) {
        if (source == null) {
            return null;
        }

        final Product product = new Product();

        product.setId(source.getId());
        product.setName(source.getName());
        product.setDiscontinued(source.isDiscontinued());
        product.setUnitsInStock(source.getUnitsInStock());
        product.setPrice(source.getPrice());
        product.setManufacturer(source.getManufacturer());
        product.setDescription(source.getDescription());
        product.setImage(source.getImage());
        //product.setCategories(source.getCategories());



        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> product.getCategories().add(categoryCommandToCategory.convert(category)));
        }



        return product;
    }


}
