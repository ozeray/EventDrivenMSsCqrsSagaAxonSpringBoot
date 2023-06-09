package com.ahmet.product.core.command.rest;

import com.ahmet.product.core.command.CreateProductCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    //    @GetMapping("/1")
//    public String get() {
//        System.out.println("GET request received");
//        return "Test: " + env.getProperty("local.server.port");
//    }

    @PostMapping("/add")
    public String add(@RequestBody @Valid CreateProductRestModel product) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                                                        .price(product.getPrice())
                                                        .title(product.getTitle())
                                                        .quantity(product.getQuantity())
                                                        .productId(UUID.randomUUID().toString())
                                                        .build();
        String returnValue;
        try {
            returnValue = commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            returnValue = e.getLocalizedMessage();
        }

        return returnValue;
    }

//    @DeleteMapping("/delete")
//    public String delete() {
//        return "Deleted";
//    }
//
//    @PutMapping("/update")
//    public String update() {
//        return "Updated";
//    }
}
