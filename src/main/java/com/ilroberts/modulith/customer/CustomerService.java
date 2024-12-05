package com.ilroberts.modulith.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    Customer updateCustomer(CustomerId id, Customer customer);

    void deleteCustomer(CustomerId id);

    Optional<Customer> getCustomer(CustomerId id);

    List<Customer> getAllCustomers();
}
