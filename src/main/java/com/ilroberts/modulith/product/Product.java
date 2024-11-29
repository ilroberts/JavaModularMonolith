package com.ilroberts.modulith.product;

import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jmolecules.ddd.types.AggregateRoot;

import java.math.BigDecimal;

@ToString(callSuper = true)
@Builder
@Getter
@AllArgsConstructor
public class Product implements AggregateRoot<Product, ProductId> {

    private final @Column(unique = true) ProductId id;

    private final String name;
    private final String description;
    private final BigDecimal price;
}
