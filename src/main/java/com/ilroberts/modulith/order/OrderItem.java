package com.ilroberts.modulith.order;

import lombok.Builder;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Table("ORDER_ITEM")
@Entity
public record OrderItem(@Id @Identity Long id, Long productId, int quantity, BigDecimal price) {}