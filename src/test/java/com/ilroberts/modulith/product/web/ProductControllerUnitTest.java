package com.ilroberts.modulith.product.web;

import com.ilroberts.modulith.product.Product;
import com.ilroberts.modulith.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void addProduct() throws Exception {
        Product product = new Product(null, "Product1", "Description1", BigDecimal.valueOf(10.0));
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\",\"description\":\"Description1\",\"price\":10.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(productService, times(1)).addProduct(any(Product.class));
    }

    @Test
    void updateProduct() throws Exception {
        Product product = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0));
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Product1\",\"description\":\"Description1\",\"price\":10.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(productService, times(1)).updateProduct(anyLong(), any(Product.class));
    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    void getProduct() throws Exception {
        Product product = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0));
        when(productService.getProduct(anyLong())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.description").value("Description1"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(productService, times(1)).getProduct(anyLong());
    }

    @Test
    void getAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0)),
                new Product(2L, "Product2", "Description2", BigDecimal.valueOf(20.0))
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].name").value("Product2"))
                .andExpect(jsonPath("$[1].description").value("Description2"))
                .andExpect(jsonPath("$[1].price").value(20.0));

        verify(productService, times(1)).getAllProducts();
    }
}