package com.ilroberts.modulith.order;

import lombok.Builder;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Builder
@Table("ORDERS")
@AggregateRoot
public record Order(@Id @Identity Long id, Long customerId, Set<OrderItem> orderItems) {}