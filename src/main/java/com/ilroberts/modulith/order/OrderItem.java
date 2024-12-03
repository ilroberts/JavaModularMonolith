package com.ilroberts.modulith.order;

import lombok.Builder;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Table("ORDER_ITEM")
@ValueObject
public record OrderItem(Long productId, int quantity, BigDecimal price) {}