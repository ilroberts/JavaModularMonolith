package com.ilroberts.modulith.order;

import com.ilroberts.modulith.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_id_seq")
    @SequenceGenerator(name = "orderitem_id_seq", sequenceName = "orderitem_id_seq", allocationSize = 1)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    public int quantity;
    public BigDecimal price;
}
