package com.ilroberts.modulith.order;

import com.ilroberts.modulith.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface Orders extends CrudRepository<Order, Long> {
}

