package com.ilroberts.modulith.order;

import com.ilroberts.modulith.product.Product;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Table("order_item")
public record OrderItem(@Id Long id, Long productId, int quantity, BigDecimal price) {}

