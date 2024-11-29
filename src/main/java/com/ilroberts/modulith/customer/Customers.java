package com.ilroberts.modulith.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface Customers extends CrudRepository<Customer, Long> {
}
