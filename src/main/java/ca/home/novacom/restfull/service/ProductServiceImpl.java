package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>();
    }

    @Override
    public Product getOneProduct(Long id) {
        return new Product();
    }

    @Override
    public Product createProduct(Product product) {
        return product;
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        return product;
    }

    @Override
    public HttpStatus deleteProduct(Long id) {
        return HttpStatus.OK;
    }

}
