package com.ilroberts.modulith.product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProducts();

}
