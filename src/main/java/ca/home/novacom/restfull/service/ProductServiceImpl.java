package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Basket;
import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.domain.User;
import ca.home.novacom.restfull.exception.NotFoundException;
import ca.home.novacom.restfull.repository.ProductRepository;
import ca.home.novacom.restfull.utils.ProductSpec;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static ca.home.novacom.restfull.utils.AnnotationHandler.getFields;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BasketService basketService;
    private final UserService userService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            BasketService basketService,
            UserService userService
    ) {
        this.productRepository = productRepository;
        this.basketService = basketService;
        this.userService = userService;
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

    /**
     * Method return list product by a string condition
     * if condition null empty, method return all product
     *
     * @param condition: type string
     */

    @Override
    public List<Product> universalSearch(String condition) {
        ProductSpec productSpec = new ProductSpec();
        if (condition == null || condition.isEmpty()) {
            return productRepository.findAll();
        } else {
            productSpec.addListFilter(getFields(Product.class, condition));
            return productRepository.findAll(productSpec);
        }
    }

    /**
     * Method creat new basket and add to database
     */

    @Override
    public HttpStatus addToBasket(User user, List<Product> listProduct) {
        try {
            userService.createUser(user);
            Basket basket = new Basket(user, LocalDateTime.now(), listProduct);
            basketService.createBasket(basket);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
