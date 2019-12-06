package ca.home.novacom.restfull;

import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.domain.User;
import ca.home.novacom.restfull.exception.NotFoundException;
import ca.home.novacom.restfull.repository.BasketRepository;
import ca.home.novacom.restfull.repository.ProductRepository;
import ca.home.novacom.restfull.service.BasketServiceImpl;
import ca.home.novacom.restfull.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
public class ServiceProductTest {

    private ProductServiceImpl productService;
    private BasketServiceImpl basketService;
    private Product product;

    @Autowired
    ProductRepository productRepository;
    BasketRepository basketRepository;

    @BeforeEach
    void setUp() {
        product = new Product("firstProduct", 29.99, "description");
        basketService = new BasketServiceImpl(basketRepository);
        productService = new ProductServiceImpl(productRepository, basketService);

    }

    private void productComparison(Product lastProduct) {
        assertEquals(product.getId(), lastProduct.getId());
        assertEquals(product.getName(), lastProduct.getName());
        assertEquals(product.getDescription(), lastProduct.getDescription());
    }

    @Test
    void findAllProduct() throws Exception {
        productRepository.save(product);
        List<Product> products = productService.getAllProducts();
        Product lastProduct = products.get(products.size() - 1);
        productComparison(lastProduct);
    }

    @Test
    void findOneProduct() throws Exception {
        productRepository.save(product);
        Product lastProduct = productService.getOneProduct(1l);
        productComparison(lastProduct);
    }

    @Test
    void createProduct() throws Exception {
        productService.createProduct(product);
        List<Product> products = productService.getAllProducts();
        Product lastProduct = products.get(products.size() - 1);
        productComparison(lastProduct);
    }

    @Test
    void updateProduct() throws Exception {
        productRepository.save(product);
        product.setName("new Name");
        Product finalProduct = productService.updateProduct(product, product.getId());
        productComparison(finalProduct);
    }

    @Test
    void deleteProduct() throws Exception {
        productRepository.save(product);
        assertEquals(HttpStatus.OK, productService.deleteProduct(product.getId()));
    }

    @Test
    void deleteSpendNotFoundException(){
        productRepository.save(product);
        assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(4l);
        });
    }

    @Test
    void universalSearch() throws Exception {
        Product product1 = new Product("firstProduct", 29.99, "description");
        Product product2 = new Product("secondProduct", 29.99, "firstProduct");
        Product product3 = new Product("threeProduct", 59.99, "description3");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        List<Product> products1 = productService.universalSearch("firstProduct");
        assertEquals(products1.size(), 2);
        List<Product> products2 = productService.universalSearch(null);
        assertEquals(products2.size(), 3);
    }

    @Test
    void addToBasket() throws Exception {
        List<Product> listProduct = new ArrayList<>();
        productRepository.save(product);
        listProduct.add(product);
        User user = new User("dmitrii", "dmitrii@dmitrii.com", LocalDateTime.now());
        user.setId(1l);
        assertEquals(HttpStatus.OK, productService.addToBasket(user, listProduct));
    }
}
