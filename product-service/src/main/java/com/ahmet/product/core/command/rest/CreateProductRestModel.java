package com.ahmet.product.core.command.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRestModel {

    @NotBlank(message = "Product title is required")
    private String title;

    @Min(value = 1, message = "Product price cannot be less than 1")
    private BigDecimal price;

    @Min(value = 1, message = "Product quantity cannot be less than 1")
    @Max(value = 5, message = "Product quantity cannot be greater than 5")
    private Double quantity;
}
