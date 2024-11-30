package com.ilroberts.modulith.order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> addOrder(Order order);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

    Order getOrder(Long id);

    List<Order> getAllOrders();
}
