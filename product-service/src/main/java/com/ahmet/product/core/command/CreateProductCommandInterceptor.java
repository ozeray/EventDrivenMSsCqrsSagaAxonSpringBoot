package com.ahmet.product.core.command;

import com.ahmet.product.core.data.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        // Integer: command index
        return (index, command) -> {
            LOGGER.info("Intercepted command:" + command.getPayloadType());
            if (command.getPayload() instanceof CreateProductCommand cpc) {
                if (productLookupRepository.findByProductIdOrTitle(cpc.getProductId(), cpc.getTitle()).isPresent()) {
                    throw new IllegalStateException(String.format("Product with productId %s or title %s already exists", cpc.getProductId(), cpc.getTitle()));
                }
                LOGGER.info("A create product command");
            }
            return command;
        };
    }
}
