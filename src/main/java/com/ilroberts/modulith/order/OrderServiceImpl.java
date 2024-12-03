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
        var customer = customerService.getCustomer(order.customerId());
        if (customer.isEmpty()) {
            return Optional.empty();
        }

        // check if all products exist
        for (OrderItem item : order.orderItems()) {
            var product = productService.getProduct(item.productId());
            if (product.isEmpty()) {
                return Optional.empty();
            }
        }
        var newOrder = Order.builder()
                .customerId(order.customerId())
                .orderItems(order.orderItems())
                .build();

        return Optional.of(orders.save(newOrder));
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Optional<Order> existingOrder = orders.findById(id);
        if (existingOrder.isPresent()) {
            if (order.customerId() != null) {
                var customer = customerService.getCustomer(order.customerId());
                if (customer.isEmpty()) {
                    throw new RuntimeException("Customer not found");
                }
            }
            Order updatedOrder = existingOrder.get();
            Order newOrder = Order.builder()
                    .id(updatedOrder.id())
                    .customerId(order.customerId())
                    .orderItems(order.orderItems() != null ? order.orderItems() : updatedOrder.orderItems())
                    .build();
            return orders.save(newOrder);
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
