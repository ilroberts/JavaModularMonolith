package com.ilroberts.modulith.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService{

    private final Customers customers;

    public Customer addCustomer(Customer customer) {

        Customer newCustomer = Customer.builder()
                .name(customer.name())
                .email(customer.email())
                .build();
        return customers.save(newCustomer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        return customers.findById(id)
                .map(existingCustomer -> {
                    var updatedCustomer = Customer.builder()
                            .id(existingCustomer.id())
                            .name(customer.name())
                            .email(customer.email())
                            .build();
                    return customers.save(updatedCustomer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customers.deleteById(id);
    }

    public Optional<Customer> getCustomer(Long id) {
        return customers.findById(id);
    }

    public List<Customer> getAllCustomers() {
        Iterable<Customer> customerIterator = customers.findAll();
        return StreamSupport.stream(customerIterator.spliterator(), false)
                .toList();
    }
}
