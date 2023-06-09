package com.ahmet.order.command.rest;

import lombok.Data;

@Data
public class CreateOrderRestModel {

    private String productId;
    private String addressId;
    private int quantity;
}
