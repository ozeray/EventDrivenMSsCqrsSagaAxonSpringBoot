package com.ahmet.order.saga;

import com.ahmet.core.command.ReseverProductCommand;
import com.ahmet.core.events.ProductReservedEvent;
import com.ahmet.order.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReseverProductCommand reseverProductCommand = ReseverProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId()).build();

        LOGGER.info("OrderCreatedEvent handled for order id: " + orderCreatedEvent.getOrderId() +
                " and product id: " + orderCreatedEvent.getProductId());
        commandGateway.send(reseverProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                // Start a compensating TX
            }
        });
    }

    @SagaEventHandler(associationProperty = "productId")
    public void handle(ProductReservedEvent productReservedEvent) {
        LOGGER.info("ProductReservedEvent handled for order id: " + productReservedEvent.getOrderId() +
                " and product id: " + productReservedEvent.getProductId());
        // Process payment
    }
}
