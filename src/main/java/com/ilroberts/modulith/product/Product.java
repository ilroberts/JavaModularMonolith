package com.ilroberts.modulith.product;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jmolecules.ddd.types.AggregateRoot;

import java.math.BigDecimal;

@Entity
@ToString(callSuper = true)
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private @Column(unique = true) Long id;

    private String name;
    private String description;
    private BigDecimal price;
}
