package com.ilroberts.modulith.product;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Builder
@Table("product")
public record Product(@Id Long id, String name, String description, BigDecimal price) {}

