package com.ilroberts.modulith.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
class CustomerServiceImpl implements CustomerService{

    private final Customers customers;

    public Customer addCustomer(Customer customer) {

        Customer newCustomer = Customer.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .build();

        // register the new customer event
        newCustomer.initialize();

        log.info("New customer created: {}", newCustomer);
        return customers.save(newCustomer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        var optionalCustomer = customers.findById(customer.getId());

        var updatedCustomer = optionalCustomer.map( c -> {
            return Customer.builder()
                    .id(c.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .build();
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
        customers.save(updatedCustomer);
        return updatedCustomer;
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
