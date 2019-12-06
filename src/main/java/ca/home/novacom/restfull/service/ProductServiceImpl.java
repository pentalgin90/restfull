package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.utils.AnnotationHandler;
import ca.home.novacom.restfull.utils.FilterProduct;
import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.utils.ProductSpec;
import ca.home.novacom.restfull.exception.NotFoundException;
import ca.home.novacom.restfull.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static ca.home.novacom.restfull.utils.AnnotationHandler.getFields;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getOneProduct(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product productFromDB = productRepository.getOne(id);
        BeanUtils.copyProperties(product, productFromDB, "id");
        return productRepository.save(productFromDB);
    }

    @Override
    public HttpStatus deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return HttpStatus.OK;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Product> universalSearch(String condition) {
        ProductSpec productSpec = new ProductSpec();
        if(condition == null || condition.isEmpty()){
            return productRepository.findAll();
        } else {
            productSpec.addListFilter(getFields(Product.class, condition));
            return productRepository.findAll(productSpec);
        }
    }
}
