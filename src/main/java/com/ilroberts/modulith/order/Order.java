package com.ilroberts.modulith.order;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Builder
@Table("orders")
public record Order(@Id Long id, Long customerId, Set<OrderItem> items) {}


