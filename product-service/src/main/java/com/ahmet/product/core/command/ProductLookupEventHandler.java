package com.ahmet.product.core.command;

import com.ahmet.product.core.data.ProductLookup;
import com.ahmet.product.core.data.ProductLookupRepository;
import com.ahmet.product.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group") // If no name is given here, by default the package name will be used.
// So if event handlers are in the same package, they will share the same group name.
// With this group, Axon by default will create its own tracking event processor, which will use a special tracking token to avoid
// multiple processing of the same event in different threads or nodes. But tracking processor spawns a different thread
// for processing, which makes it impossible to catch the errors in controller methods.
// By grouping event handlers in the same group, we want to ensure that these event handlers are processed once
// and in the same thread. This will help us in rolling whole TX back in case event processing is unsuccessful.
// For all these to work, we need to assign an event processor in application.properties file (e.g. subscribing
// event processor:
// axon.eventhandling.processors.product-group.mode=subscribing).
public class ProductLookupEventHandler {

    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEventHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookup productLookup = new ProductLookup(event.getProductId(), event.getTitle());
        productLookupRepository.save(productLookup);
    }
}
