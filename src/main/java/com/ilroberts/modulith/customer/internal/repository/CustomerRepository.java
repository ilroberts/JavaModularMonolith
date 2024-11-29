package com.ilroberts.modulith.customer.internal.repository;

import com.ilroberts.modulith.customer.internal.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
