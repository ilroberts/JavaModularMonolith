package com.ilroberts.modulith.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTest {

    ProductService productService;

    @Mock
    Products productsRepository;

    @BeforeEach
    void setUp() {
        this.productService = new ProductServiceImpl(productsRepository);
    }

    @Test
    void addProduct() {
        Product product = new Product(null, "Product1", "Description1", BigDecimal.valueOf(10.0));
        when(productsRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.name()).isEqualTo("Product1");
        assertThat(savedProduct.description()).isEqualTo("Description1");
        assertThat(savedProduct.price()).isEqualByComparingTo(BigDecimal.valueOf(10.0));
        verify(productsRepository, times(1)).save(product);
    }

    @Test
    void updateProduct() {
        Product existingProduct = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0));
        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", BigDecimal.valueOf(20.0));

        when(productsRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productsRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("UpdatedProduct");
        assertThat(result.description()).isEqualTo("UpdatedDescription");
        assertThat(result.price()).isEqualByComparingTo(BigDecimal.valueOf(20.0));
        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProductNotFound() {
        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", BigDecimal.valueOf(20.0));

        when(productsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(1L, updatedProduct))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found with id 1");
        verify(productsRepository, times(1)).findById(1L);
        verify(productsRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        doNothing().when(productsRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productsRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProduct() {
        Product product = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0));

        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProduct(1L);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("Product1");
        assertThat(result.get().description()).isEqualTo("Description1");
        assertThat(result.get().price()).isEqualByComparingTo(BigDecimal.valueOf(10.0));
        verify(productsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0));
        Product product2 = new Product(2L, "Product2", "Description2", BigDecimal.valueOf(20.0));

        when(productsRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertThat(products).isNotNull();
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::name).containsExactly("Product1", "Product2");
        verify(productsRepository, times(1)).findAll();
    }
}
