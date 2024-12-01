package com.ilroberts.modulith.order;

import com.ilroberts.modulith.customer.Customer;
import com.ilroberts.modulith.customer.CustomerService;
import com.ilroberts.modulith.product.Product;
import com.ilroberts.modulith.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplUnitTest {

    OrderService orderService;

    @Mock
    Orders ordersRepository;

    @Mock
    CustomerService customerService;

    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        this.orderService = new OrderServiceImpl(ordersRepository, customerService, productService);
    }

    @Test
    void addOrder() {
        Order order = new Order(null, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(customerService.getCustomer(1L)).thenReturn(Optional.of(new Customer(1L, "John Doe", "john.doe@example.com")));
        when(productService.getProduct(1L)).thenReturn(Optional.of(new Product(1L, "Product1", "Description1", BigDecimal.valueOf(10.0))));
        when(ordersRepository.save(any(Order.class))).thenReturn(order);

        Optional<Order> savedOrder = orderService.addOrder(order);

        assertThat(savedOrder).isPresent();
        assertThat(savedOrder.get().customerId()).isEqualTo(1L);
        assertThat(savedOrder.get().items()).hasSize(1);
        verify(ordersRepository, times(1)).save(order);
    }

    @Test
    void addOrderCustomerNotFound() {
        Order order = new Order(null, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(customerService.getCustomer(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.addOrder(order);

        assertThat(result).isEmpty();
        verify(ordersRepository, never()).save(any(Order.class));
    }

    @Test
    void addOrderProductNotFound() {
        Order order = new Order(null, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(customerService.getCustomer(1L)).thenReturn(Optional.of(new Customer(1L, "John Doe", "john.doe@example.com")));
        when(productService.getProduct(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.addOrder(order);

        assertThat(result).isEmpty();
        verify(ordersRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrder() {
        Order existingOrder = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        Order updatedOrder = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 3, BigDecimal.valueOf(10.0))));
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(customerService.getCustomer(1L)).thenReturn(Optional.of(new Customer(1L, "John Doe", "john.doe@example.com")));
        when(ordersRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertThat(result).isNotNull();
        assertThat(result.items()).hasSize(1);
        assertThat(result.items().iterator().next().quantity()).isEqualTo(3);
        verify(ordersRepository, times(1)).findById(1L);
        verify(ordersRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrderNotFound() {
        Order updatedOrder = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 3, BigDecimal.valueOf(10.0))));
        when(ordersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.updateOrder(1L, updatedOrder))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Order not found");
        verify(ordersRepository, times(1)).findById(1L);
        verify(ordersRepository, never()).save(any(Order.class));
    }

    @Test
    void deleteOrder() {
        doNothing().when(ordersRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(ordersRepository, times(1)).deleteById(1L);
    }

    @Test
    void getOrder() {
        Order order = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertThat(result).isNotNull();
        assertThat(result.customerId()).isEqualTo(1L);
        assertThat(result.items()).hasSize(1);
        verify(ordersRepository, times(1)).findById(1L);
    }

    @Test
    void getAllOrders() {
        Order order1 = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        Order order2 = new Order(2L, 2L, Set.of(new OrderItem(2L, 2L, 3, BigDecimal.valueOf(20.0))));
        when(ordersRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertThat(orders).isNotNull();
        assertThat(orders).hasSize(2);
        verify(ordersRepository, times(1)).findAll();
    }
}