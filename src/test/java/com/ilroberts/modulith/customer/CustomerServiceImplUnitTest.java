package com.ilroberts.modulith.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplUnitTest {

    CustomerService customerService;

    @Mock
    Customers customersRepository;


    @BeforeEach
    void setUp() {
        this.customerService = new CustomerServiceImpl(customersRepository);
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer(null, "John Doe", "john.doe@example.com");
        when(customersRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.addCustomer(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.name()).isEqualTo("John Doe");
        assertThat(savedCustomer.email()).isEqualTo("john.doe@example.com");
        verify(customersRepository, times(1)).save(customer);
    }

    @Test
    void updateCustomer() {
        Customer existingCustomer = new Customer(1L,"John Doe", "john.doe@example.com");
        Customer updatedCustomer = new Customer(null,"Jane Doe", "jane.doe@example.com");

        when(customersRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customersRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Jane Doe");
        assertThat(result.email()).isEqualTo("jane.doe@example.com");
        verify(customersRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).save(existingCustomer);
    }

    @Test
    void deleteCustomer() {
        doNothing().when(customersRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customersRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer(1L,"John Doe", "john.doe@example.com");

        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L).get();

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("John Doe");
        assertThat(result.email()).isEqualTo("john.doe@example.com");
        verify(customersRepository, times(1)).findById(1L);
    }

    @Test
    void getAllCustomers() {
        Customer customer1 = new Customer(null,"John Doe", "john.doe@example.com");
        Customer customer2 = new Customer(null,"Jane Doe", "jane.doe@example.com");

        when(customersRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(2);
        verify(this.customersRepository, times(1)).findAll();
    }
}