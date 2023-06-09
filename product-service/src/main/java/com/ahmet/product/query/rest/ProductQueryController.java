package com.ahmet.product.query.rest;


import com.ahmet.product.core.query.QueryProductRestModel;
import com.ahmet.product.query.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<QueryProductRestModel> getProducts() {
        List<QueryProductRestModel> list = queryGateway.query(new FindProductsQuery(),
                ResponseTypes.multipleInstancesOf(QueryProductRestModel.class)).join();
        return list;
    }
}
