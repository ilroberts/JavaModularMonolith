package com.ilroberts.modulith.customer.web;

import com.ilroberts.modulith.customer.Customer;
import com.ilroberts.modulith.customer.CustomerId;
import com.ilroberts.modulith.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = new Customer(null, customerDTO.name(), customerDTO.email());
        Customer savedCustomer = customerService.addCustomer(customer);
        return new CustomerDTO(savedCustomer.getId().id(), savedCustomer.getName(), savedCustomer.getEmail());
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(CustomerId.of(id), customerDTO.name(), customerDTO.email());
        Customer updatedCustomer = customerService.updateCustomer(CustomerId.of(id), customer);
        return new CustomerDTO(updatedCustomer.getId().id(), updatedCustomer.getName(), updatedCustomer.getEmail());
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(CustomerId.of(id));
    }

    @GetMapping("/{id}")
    public Optional<CustomerDTO> getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(CustomerId.of(id))
                .map(customer -> new CustomerDTO(customer.getId().id(), customer.getName(), customer.getEmail()));
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(customer -> new CustomerDTO(customer.getId().id(), customer.getName(), customer.getEmail()))
                .collect(Collectors.toList());
    }
}