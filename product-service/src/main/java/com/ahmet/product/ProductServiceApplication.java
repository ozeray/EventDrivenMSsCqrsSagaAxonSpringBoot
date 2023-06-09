package com.ahmet.product;

import com.ahmet.product.core.command.CreateProductCommandInterceptor;
import com.ahmet.product.core.error.ProductServiceEventsHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.ahmet.product")
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("product-group",
				config -> new ProductServiceEventsHandler());
		// Or use the default impl. by Axon (exactly same as what ProductServiceEventsHandler does):
//		configurer.registerListenerInvocationErrorHandler("product-group",
//				config -> PropagatingErrorHandler.instance());
	}
}
