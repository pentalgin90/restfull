package ca.home.novacom.restfull;

import ca.home.novacom.restfull.domain.Product;
import ca.home.novacom.restfull.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerProductTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProduct() throws Exception {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("name1", 299.99, "description1"));
        productList.add(new Product("name2", 39.99, "description2"));
        when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void getOneProduct() throws Exception {
        Product product = new Product("name1", 29.99, "description1");
        when(productService.getOneProduct(1l)).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
