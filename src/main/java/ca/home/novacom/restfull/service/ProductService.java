package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getOneProduct(Long id);
}
