package com.ilroberts.modulith.customer.web;

import com.ilroberts.modulith.customer.Customer;
import com.ilroberts.modulith.customer.CustomerId;
import com.ilroberts.modulith.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void addCustomer() throws Exception {
        Customer customer = new Customer(CustomerId.of(1L), "John Doe", "john.doe@example.com");
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void updateCustomer() throws Exception {
        Customer customer = new Customer(CustomerId.of(1L), "John Doe", "john.doe@example.com");
        when(customerService.updateCustomer(any(CustomerId.class), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).updateCustomer(any(CustomerId.class), any(Customer.class));
    }

    @Test
    void deleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(any(CustomerId.class));

        mockMvc.perform(delete("/api/customer/1"))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(any(CustomerId.class));
    }

    @Test
    void getCustomer() throws Exception {
        Customer customer = new Customer(CustomerId.of(1L), "John Doe", "john.doe@example.com");
        when(customerService.getCustomer(any(CustomerId.class))).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService, times(1)).getCustomer(any(CustomerId.class));
    }

    @Test
    void getAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(
                new Customer(CustomerId.of(1L), "John Doe", "john.doe@example.com"),
                new Customer(CustomerId.of(2L), "Jane Doe", "jane.doe@example.com")
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));

        verify(customerService, times(1)).getAllCustomers();
    }
}