package com.ilroberts.modulith.customer;


import org.springframework.data.repository.CrudRepository;

interface Customers extends CrudRepository<Customer, CustomerId> {

}
