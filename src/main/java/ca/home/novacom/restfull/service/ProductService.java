package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Product;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getOneProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product, Long id);
    HttpStatus deleteProduct(Long id);
}
