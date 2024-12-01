package com.ilroberts.modulith.order.web;

import com.ilroberts.modulith.order.Order;
import com.ilroberts.modulith.order.OrderItem;
import com.ilroberts.modulith.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void addOrder() throws Exception {
        Order order = new Order(null, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(orderService.addOrder(any(Order.class))).thenReturn(Optional.of(order));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"items\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.items[0].productId").value(1L))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].price").value(10.0));

        verify(orderService, times(1)).addOrder(any(Order.class));
    }

    @Test
    void updateOrder() throws Exception {
        Order order = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(orderService.updateOrder(anyLong(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"items\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.items[0].productId").value(1L))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].price").value(10.0));

        verify(orderService, times(1)).updateOrder(anyLong(), any(Order.class));
    }

    @Test
    void deleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(anyLong());

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(anyLong());
    }

    @Test
    void getOrder() throws Exception {
        Order order = new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0))));
        when(orderService.getOrder(anyLong())).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.items[0].productId").value(1L))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].price").value(10.0));

        verify(orderService, times(1)).getOrder(anyLong());
    }

    @Test
    void getAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                new Order(1L, 1L, Set.of(new OrderItem(1L, 1L, 2, BigDecimal.valueOf(10.0)))),
                new Order(2L, 2L, Set.of(new OrderItem(2L, 2L, 3, BigDecimal.valueOf(20.0))))
        );
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1L))
                .andExpect(jsonPath("$[0].items[0].productId").value(1L))
                .andExpect(jsonPath("$[0].items[0].quantity").value(2))
                .andExpect(jsonPath("$[0].items[0].price").value(10.0))
                .andExpect(jsonPath("$[1].customerId").value(2L))
                .andExpect(jsonPath("$[1].items[0].productId").value(2L))
                .andExpect(jsonPath("$[1].items[0].quantity").value(3))
                .andExpect(jsonPath("$[1].items[0].price").value(20.0));

        verify(orderService, times(1)).getAllOrders();
    }
}