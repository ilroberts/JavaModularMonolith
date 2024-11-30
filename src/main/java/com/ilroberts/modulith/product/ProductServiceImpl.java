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

    public Product updateProduct(Long id, Product productDetails) {
        validateProduct(productDetails);
        Optional<Product> optionalProduct = products.findById(id);
        if (optionalProduct.isPresent()) {
            Product originalProduct = optionalProduct.get();
            var updatedProduct = Product.builder()
                    .id(originalProduct.id())
                    .name(productDetails.name())
                    .description(productDetails.description())
                    .price(productDetails.price())
                    .build();
            return products.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found with id " + id);
        }
    }

    public void deleteProduct(Long id) {
        products.deleteById(id);
    }

    public Optional<Product> getProduct(Long id) {
        return products.findById(id);
    }

    public List<Product> getAllProducts() {
        Iterable<Product> iterable = products.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void validateProduct(Product product) {
        if (product.name() == null || product.name().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.description() == null || product.description().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
    }
}
