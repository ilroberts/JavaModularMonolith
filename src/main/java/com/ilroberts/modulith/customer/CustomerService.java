package com.ilroberts.modulith.customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();
}
