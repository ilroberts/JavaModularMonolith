package com.ilroberts.modulith.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {

    @Autowired
    private Products products;

    public Product addProduct(Product product) {
        validateProduct(product);
        return products.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        validateProduct(productDetails);
        Optional<Product> optionalProduct = products.findById(ProductId.of(id));
        if (optionalProduct.isPresent()) {
            Product originalProduct = optionalProduct.get();
            var updatedProduct = Product.builder()
                    .id(originalProduct.getId())
                    .name(productDetails.getName())
                    .description(productDetails.getDescription())
                    .price(productDetails.getPrice())
                    .build();
            return products.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found with id " + id);
        }
    }

    public void deleteProduct(String id) {
        products.deleteById(ProductId.of(id));
    }

    public Product getProduct(String id) {
        return products.findById(ProductId.of(id)).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public List<Product> getAllProducts() {
        Iterable<Product> iterable = products.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
    }
}
