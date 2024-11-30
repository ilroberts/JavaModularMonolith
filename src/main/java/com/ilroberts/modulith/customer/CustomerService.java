package com.ilroberts.modulith.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    Optional<Customer> getCustomer(Long id);

    List<Customer> getAllCustomers();
}
