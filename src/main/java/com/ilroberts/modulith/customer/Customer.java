package com.ilroberts.modulith.customer;

import lombok.Builder;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table("customer")
@AggregateRoot
public record Customer(@Id @Identity Long id, String name, String email) {}

