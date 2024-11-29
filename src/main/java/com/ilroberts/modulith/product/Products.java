package com.ilroberts.modulith.product;

import org.springframework.data.repository.CrudRepository;

interface Products extends CrudRepository<Product, ProductId> {
}
