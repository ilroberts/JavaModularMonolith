package com.ilroberts.modulith.customer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table("customer")
public record Customer(@Id Long id, String name, String email) {}

