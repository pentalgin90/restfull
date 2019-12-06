package ca.home.novacom.restfull;

import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.exception.NotFoundException;
import ca.home.novacom.restfull.repository.ProductRepository;
import ca.home.novacom.restfull.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
public class ServiceProductTest {

    private ProductServiceImpl productService;
    private Product product;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        product = new Product("firstProduct", 29.99, "description");
        productService = new ProductServiceImpl(productRepository);
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
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = productService.universalSearch("firstProduct");
        products.forEach(x -> System.out.println(x.toString()));
    }
}