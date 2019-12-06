package ca.home.novacom.restfull.controller;

import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.domain.User;
import ca.home.novacom.restfull.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Method return list products in the format JSON
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    /**
     * Method return one product in the format JSON
     *
     * @param id : identifier product in format Long
     */
    @GetMapping("{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getOneProduct(id), HttpStatus.OK);
    }

    /**
     * Method save object product in database
     *
     * @param product : object product for entity in format JSON
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    /**
     * Method make change product and save in database
     *
     * @param id      : identifier product in format Long
     * @param product : object product in format JSON
     */
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.OK);
    }

    /**
     * Method delete product of database
     *
     * @param id : identifier product
     */
    @DeleteMapping("{id}")
    public HttpStatus deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    /**
     * Method return list product in format JSON by condition
     * if condition there is in name product or description
     *
     * @param condition : string param
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> universalSearch(@RequestParam(name = "filter", required = true) String condition) {
        return new ResponseEntity<>(productService.universalSearch(condition), HttpStatus.OK);
    }

    /**
     * Method create basket with list product and user
     * @param listProduct: list product
     */
    @PostMapping("/buy/{id}")
    public HttpStatus addBasket(@RequestBody List<Product> listProduct) {
        User user = new User("dmitrii", "dmitrii@dmitrii.com", LocalDateTime.now());
        // TODO if will be autorisation, User will be from autorisation
        return productService.addToBasket(user, listProduct);
    }
}
