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

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getOneProduct(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id){
        return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> universalSearch(@RequestParam(name = "filter", required = true) String condition){
        return new ResponseEntity<>(productService.universalSearch(condition), HttpStatus.OK);
    }

    @PostMapping("/buy/{id}")
    public HttpStatus addBasket(@RequestBody List<Product> listProduct){
        User user = new User("dmitrii", "dmitrii@dmitrii.com", LocalDateTime.now());
        // TODO if will be autorisation, User will be from autorisation
        return productService.addToBasket(user, listProduct);
    }
}
