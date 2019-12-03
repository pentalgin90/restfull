package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>();
    }
}
