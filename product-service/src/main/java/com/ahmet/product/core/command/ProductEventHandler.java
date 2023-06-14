package com.ahmet.product.core.command;

import com.ahmet.core.events.ProductReservedEvent;
import com.ahmet.product.core.data.Product;
import com.ahmet.product.core.data.ProductRepository;
import com.ahmet.product.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // Handles exceptions only in this class.
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        try {
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        throw new Exception("Error occurred in ProductEventHandler > ProductCreatedEvent handler");
        // Thanks to ProductServiceEventsHandler, this exception will bubble up until Rest controller, and any staged
        // changes will roll back.
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        Optional<Product> optionalProduct = productRepository.findById(productReservedEvent.getProductId());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() - productReservedEvent.getQuantity());
            productRepository.save(product);
            LOGGER.info("ProductReservedEvent handled for order id: " + productReservedEvent.getOrderId() +
                    " and product id: " + productReservedEvent.getProductId());
        } else {
            throw new IllegalStateException("No product record found with id:" + productReservedEvent.getProductId());
        }
    }
}
