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

        customer.setId(null);
        return customers.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customers.findById(id);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setEmail(customer.getEmail());
            return customers.save(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
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
