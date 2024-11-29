package com.ilroberts.customer.service;

import com.ilroberts.modulith.customer.Customer;
import com.ilroberts.modulith.customer.Customers;
import com.ilroberts.modulith.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private Customers customers;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        when(customers.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
        assertThat(savedCustomer.getEmail()).isEqualTo("john.doe@example.com");
        verify(customers, times(1)).save(customer);
    }

    @Test
    void updateCustomer() {
        Customer existingCustomer = new Customer("John Doe", "john.doe@example.com");
        existingCustomer.setId(1L);
        Customer updatedCustomer = new Customer("Jane Doe", "jane.doe@example.com");

        when(customers.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customers.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jane Doe");
        assertThat(result.getEmail()).isEqualTo("jane.doe@example.com");
        verify(customers, times(1)).findById(1L);
        verify(customers, times(1)).save(existingCustomer);
    }

    @Test
    void deleteCustomer() {
        doNothing().when(customers).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customers, times(1)).deleteById(1L);
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customer.setId(1L);

        when(customers.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        verify(customers, times(1)).findById(1L);
    }

    @Test
    void getAllCustomers() {
        Customer customer1 = new Customer("John Doe", "john.doe@example.com");
        Customer customer2 = new Customer("Jane Doe", "jane.doe@example.com");

        when(customers.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(2);
        verify(this.customers, times(1)).findAll();
    }
}