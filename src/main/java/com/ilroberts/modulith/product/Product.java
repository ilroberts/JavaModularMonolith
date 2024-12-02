package com.ilroberts.modulith.product;

import lombok.Builder;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Table("PRODUCT")
@AggregateRoot
public record Product(@Id @Identity Long id, String name, String description, BigDecimal price) {}