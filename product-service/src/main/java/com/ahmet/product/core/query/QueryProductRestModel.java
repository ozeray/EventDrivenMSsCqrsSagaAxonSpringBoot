package com.ahmet.product.core.query;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryProductRestModel {

    private String productId;
    private String title;
    private BigDecimal price;
    private Double quantity;
}
