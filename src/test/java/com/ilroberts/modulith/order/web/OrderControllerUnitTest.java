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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
        OrderItem orderItem = new OrderItem(1L, 2, BigDecimal.valueOf(10.0));
        Order order = new Order(null, 1L, Set.of(orderItem));
        var expected = "{\"id\":null,\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}";

        when(orderService.addOrder(any(Order.class))).thenReturn(Optional.of(order));

        MockHttpServletResponse response = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expected);
        verify(orderService, times(1)).addOrder(any(Order.class));
    }

    @Test
    void updateOrder() throws Exception {
        var expected = "{\"id\":null,\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}";
        OrderItem orderItem = new OrderItem(1L, 2, BigDecimal.valueOf(10.0));
        Order order = new Order(null, 1L, Set.of(orderItem));
        when(orderService.updateOrder(anyLong(), any(Order.class))).thenReturn(order);

        MockHttpServletResponse response = mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expected);
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
        var expected = "{\"id\":null,\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}";
        OrderItem orderItem = new OrderItem(1L, 2, BigDecimal.valueOf(10.0));
        Order order = new Order(null, 1L, Set.of(orderItem));
        when(orderService.getOrder(anyLong())).thenReturn(order);

        MockHttpServletResponse response = mockMvc.perform(get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expected);
        verify(orderService, times(1)).getOrder(anyLong());
    }

    @Test
    void getAllOrders() throws Exception {
        var expected = "[{\"id\":null,\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}]";
        OrderItem orderItem = new OrderItem(1L, 2, BigDecimal.valueOf(10.0));
        Order order = new Order(null, 1L, Set.of(orderItem));
        when(orderService.getAllOrders()).thenReturn(List.of(order));

        MockHttpServletResponse response = mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderItems\":[{\"productId\":1,\"quantity\":2,\"price\":10.0}]}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expected);
        verify(orderService, times(1)).getAllOrders();
    }
}