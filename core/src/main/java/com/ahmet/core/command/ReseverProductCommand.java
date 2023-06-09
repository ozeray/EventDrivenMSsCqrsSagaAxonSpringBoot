package com.ahmet.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ReseverProductCommand {

    @TargetAggregateIdentifier // Will be used by Axon FW to find ProductAggregate in products MS
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
}
