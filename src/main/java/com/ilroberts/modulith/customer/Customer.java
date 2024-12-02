package com.ilroberts.modulith.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.event.types.DomainEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Table("CUSTOMER")
@Getter
@AllArgsConstructor
@AggregateRoot
public class Customer extends AbstractAggregateRoot<Customer> {

    @Id @Identity
    private final Long id;
    private final String name;
    private final String email;

    void initialize() {
        registerEvent(new CustomerCreatedEvent(id, name, email));
    }
    public record CustomerCreatedEvent(Long id, String name, String email) implements DomainEvent { }
}




