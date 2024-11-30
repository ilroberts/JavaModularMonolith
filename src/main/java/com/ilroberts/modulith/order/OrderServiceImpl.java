package com.ilroberts.modulith.order;

import com.ilroberts.modulith.customer.CustomerService;
import com.ilroberts.modulith.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {

    private final Orders orders;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public Optional<Order> addOrder(Order order) {
        var customer = customerService.getCustomer(order.getCustomer().getId());
        if (customer.isEmpty()) {
            return Optional.empty();
        }
        order.setId(null);
        return Optional.of(orders.save(order));
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orders.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setCustomer(order.getCustomer());
            updatedOrder.setItems(order.getItems());
            return orders.save(updatedOrder);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orders.deleteById(id);
    }

    @Override
    public Order getOrder(Long id) {
        return orders.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        Iterable<Order> orderIterator = orders.findAll();
        return StreamSupport.stream(orderIterator.spliterator(), false)
                .toList();
    }
}
