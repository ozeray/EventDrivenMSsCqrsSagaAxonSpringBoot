package com.ahmet.order.command.rest;

import com.ahmet.order.command.CreateOrderCommand;
import com.ahmet.order.command.OrderStatus;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;

    public OrdersCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public String add(@RequestBody @Valid CreateOrderRestModel order) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .quantity(order.getQuantity())
                .orderId(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.CREATED)
                .productId(order.getProductId())
                .addressId(order.getAddressId())
                .build();

        String returnValue;
        try {
            returnValue = commandGateway.sendAndWait(createOrderCommand);
        } catch (Exception e) {
            returnValue = e.getLocalizedMessage();
        }

        return returnValue;
    }
}
