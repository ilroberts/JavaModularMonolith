package com.ilroberts.modulith.product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(String id, Product productDetails);

    void deleteProduct(String id);

    Product getProduct(String id);

    List<Product> getAllProducts();

}
