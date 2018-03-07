package info.mike.webstorev1.bootstrap;

import info.mike.webstorev1.domain.Cart;
import info.mike.webstorev1.domain.CartItem;
import info.mike.webstorev1.domain.Category;
import info.mike.webstorev1.domain.Product;
import info.mike.webstorev1.repository.CategoryRepository;
import info.mike.webstorev1.repository.ProductRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebstoreBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public WebstoreBootstrap(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //productRepository.saveAll(getAllProducts());

        initData();
    }


    private void initData(){
        Category elektronika = new Category();
        elektronika.setCategoryName("Elektronika");

        Product komputer = new Product();
        komputer.setName("Komputer Infinity S700");
        komputer.setDescription("Komputer Infinity S700 został stworzony dla najbardziej wymagającej grupy " +
                "użytkowników domowych – dla graczy i osób zajmujących się obróbką wideo.");
        komputer.setManufacturer("Komputronik");
        komputer.setPrice(new BigDecimal(4999));
        komputer.setUnitsInStock(10L);
        komputer.setDiscontinued(false);

        elektronika.getProducts().add(komputer);
        komputer.getCategories().add(elektronika);

        ////////////////////////////////////////////////////////
        Category telefonyISmartwatche = new Category();
        telefonyISmartwatche.setCategoryName("Telefony i Smartwatche");


        Product lenovoK5DualSim = new Product();
        lenovoK5DualSim.setName("Lenovo K5 Dual SIM");
        lenovoK5DualSim.setDescription("Lenovo K5 Dual SIM dostępny jest w trzech stylowych kolorach.");
        lenovoK5DualSim.setManufacturer("Lenovo");
        lenovoK5DualSim.setPrice(new BigDecimal(989L));
        lenovoK5DualSim.setUnitsInStock(50L);
        lenovoK5DualSim.setDiscontinued(false);

        telefonyISmartwatche.getProducts().add(komputer);
        lenovoK5DualSim.getCategories().add(telefonyISmartwatche);

        komputer.getCategories().add(telefonyISmartwatche);
        telefonyISmartwatche.getProducts().add(komputer);

        ///////////////////////////////////////////////////////////
        Category telewizoryIRTV = new Category();
        telewizoryIRTV.setCategoryName("Telewizory i RTV");

        Product panasonicTX75EX780E = new Product();
        panasonicTX75EX780E.setName("Panasonic TX-75EX780E");
        panasonicTX75EX780E.setDescription("Funkcjonalna elegancja i wszechstronne mozliwosci połączeń to zalety to zalety, które" +
                "łączy w sobie telewizor Panasonic TX-75EX780E");
        panasonicTX75EX780E.setManufacturer("Panasonic");
        panasonicTX75EX780E.setPrice(new BigDecimal(15999));
        panasonicTX75EX780E.setUnitsInStock(30L);
        panasonicTX75EX780E.setDiscontinued(false);

        telewizoryIRTV.getProducts().add(panasonicTX75EX780E);
        panasonicTX75EX780E.getCategories().add(telewizoryIRTV);
        ////////////////////////////////////////////////////////////


        Category agd = new Category();
        agd.setCategoryName("AGD");

        Category dom = new Category();
        dom.setCategoryName("Dom");

        Category sieciikomunikacja = new Category();
        sieciikomunikacja.setCategoryName("Sieci i komunikacja");

        Category oprogramowanie = new Category();
        oprogramowanie.setCategoryName("Oprogramowanie");

        Category sprzetaudio = new Category();
        sprzetaudio.setCategoryName("Sprzęt audio");

        Category  fotoiwideo = new Category();
        fotoiwideo.setCategoryName("Foto i wideo");

        productRepository.save(komputer);
        categoryRepository.save(elektronika);
        productRepository.save(lenovoK5DualSim);
        categoryRepository.save(telefonyISmartwatche);
        productRepository.save(panasonicTX75EX780E);
        categoryRepository.save(telewizoryIRTV);

        categoryRepository.save(agd);
        categoryRepository.save(dom);
        categoryRepository.save(sieciikomunikacja);
        categoryRepository.save(oprogramowanie);
        categoryRepository.save(sprzetaudio);
        categoryRepository.save(fotoiwideo);

    }


    private List<Product> getAllProducts(){

        List<Product> products = new ArrayList<>();

        Category elektronika = new Category();
        elektronika.setCategoryName("Elektronika");

        Category telefonyISmartwatche = new Category();
        telefonyISmartwatche.setCategoryName("Telefony i Smartwatche");

        Category telewizoryIRTV = new Category();
        telewizoryIRTV.setCategoryName("Telewizory i RTV");

        Category agd = new Category();
        agd.setCategoryName("AGD");

        Category dom = new Category();
        dom.setCategoryName("Dom");

        Product komputer = new Product();
        komputer.setName("Komputer Infinity S700");
        komputer.setDescription("Komputer Infinity S700 został stworzony dla najbardziej wymagającej grupy " +
                "użytkowników domowych – dla graczy i osób zajmujących się obróbką wideo.");
        komputer.setManufacturer("Komputronik");
        komputer.setPrice(new BigDecimal(4999));
        komputer.setUnitsInStock(10L);
        komputer.setDiscontinued(false);
        komputer.getCategories().add(elektronika);

        Product lenovoK5DualSim = new Product();
        lenovoK5DualSim.setName("Lenovo K5 Dual SIM");
        lenovoK5DualSim.setDescription("Lenovo K5 Dual SIM dostępny jest w trzech stylowych kolorach.");
        lenovoK5DualSim.setManufacturer("Lenovo");
        lenovoK5DualSim.setPrice(new BigDecimal(989L));
        lenovoK5DualSim.setUnitsInStock(50L);
        lenovoK5DualSim.setDiscontinued(false);
        lenovoK5DualSim.getCategories().add(telefonyISmartwatche);
        lenovoK5DualSim.getCategories().add(elektronika);

        Product panasonicTX75EX780E = new Product();
        panasonicTX75EX780E.setName("Panasonic TX-75EX780E");
        panasonicTX75EX780E.setDescription("Funkcjonalna elegancja i wszechstronne mozliwosci połączeń to zalety to zalety, które" +
                "łączy w sobie telewizor Panasonic TX-75EX780E");
        panasonicTX75EX780E.setManufacturer("Panasonic");
        panasonicTX75EX780E.setPrice(new BigDecimal(15999));
        panasonicTX75EX780E.setUnitsInStock(30L);
        panasonicTX75EX780E.setDiscontinued(false);
        panasonicTX75EX780E.getCategories().add(telewizoryIRTV);

        Product philipsHR167690 = new Product();
        philipsHR167690.setName("Philips HR1676/90");
        philipsHR167690.getCategories().add(agd);


        Product stanleyFME1250K = new Product();
        stanleyFME1250K.setName("Stanley FME1250K");
        stanleyFME1250K.getCategories().add(dom);

        products.add(komputer);
        products.add(lenovoK5DualSim);
        products.add(panasonicTX75EX780E);
        products.add(philipsHR167690);
        products.add(stanleyFME1250K);


        return products;

    }
}
