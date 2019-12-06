package ca.home.novacom.restfull;

import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class ControllerProductTest {

    private Product product;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUP() {
        product = new Product("name1", 29.99, "description1");
    }

    @Test
    void getAllProduct() throws Exception {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("name1", 299.99, "description1"));
        productList.add(new Product("name2", 39.99, "description2"));
        when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void getOneProduct() throws Exception {
        when(productService.getOneProduct(1l)).thenReturn(product);
        mockMvc.perform(get("/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createProduct() throws Exception {
        when(productService.createProduct(product)).thenReturn(product);
        ObjectMapper mapper = new ObjectMapper();
        String productJson = mapper.writeValueAsString(product);
        ResultActions resultActions = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson));
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void updateProduct() throws Exception {
        when(productService.updateProduct(product, product.getId())).thenReturn(product);
        ObjectMapper mapper = new ObjectMapper();
        String productJson = mapper.writeValueAsString(product);
        ResultActions resultActions = mockMvc.perform(put("/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        );
        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void ubiversalProduct() throws Exception {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("name1", 299.99, "description1"));
        productList.add(new Product("name2", 39.99, "name1"));
        when(productService.universalSearch("name1")).thenReturn(productList);
        mockMvc.perform(get("/product/search").param("filter", "name1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void buyProduct() throws Exception {
        mockMvc.perform(post("/product/buy/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
