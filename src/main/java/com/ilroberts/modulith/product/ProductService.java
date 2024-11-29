package com.ilroberts.modulith.product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);

    Product getProduct(Long id);

    List<Product> getAllProducts();

}
