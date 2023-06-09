package com.ahmet.order.events;

import com.ahmet.order.data.OrderEntity;
import com.ahmet.order.data.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsHandler {

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity order = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, order);
        orderRepository.save(order);
    }
}
