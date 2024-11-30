package com.ilroberts.modulith.order;

import com.ilroberts.modulith.customer.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "orders")
@NoArgsConstructor
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
        @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
        private Long id;

        @ManyToOne
        private Customer customer;

        @OneToMany
        @JoinColumn(name = "order_id")
        private List<OrderItem> items = new ArrayList<>();
}

