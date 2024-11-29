package com.ilroberts.modulith.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface Products extends CrudRepository<Product, ProductId> {
}
