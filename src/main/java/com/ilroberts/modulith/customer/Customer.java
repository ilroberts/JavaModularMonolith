package com.ilroberts.modulith.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.event.types.DomainEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.relational.core.mapping.Table;

@Slf4j
@Table("CUSTOMER")
@Getter
@Builder
@AllArgsConstructor
public class Customer extends AbstractAggregateRoot<Customer>
        implements AggregateRoot<Customer, CustomerId> {

    @Id
    private final CustomerId id;
    private final String name;
    private final String email;

    public static Customer copy(Customer customer) {
        return Customer.builder()
                .id(customer.id)
                .name(customer.name)
                .email(customer.email)
                .build();
    }

    public Customer initialize() {
        log.info("creating new customer");
        var eventId = this.id == null ? null : this.id.id();

        var event = new CustomerCreatedEvent(eventId, name, email);
        log.info("created new event: {}", event);

        registerEvent(event);
        return this;
    }

    public record CustomerCreatedEvent(Long id, String name, String email) implements DomainEvent { }
}




