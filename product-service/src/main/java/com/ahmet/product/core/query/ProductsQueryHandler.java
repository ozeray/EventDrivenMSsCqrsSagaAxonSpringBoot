package com.ahmet.product.core.query;

import com.ahmet.product.core.data.ProductRepository;
import com.ahmet.product.query.FindProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsQueryHandler {

    private final ProductRepository productRepository;

    public ProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<QueryProductRestModel> findProducts(FindProductsQuery findProductsQuery) {
        List<QueryProductRestModel> list = new ArrayList<>(productRepository.findAll().stream()
                .map(product -> {
                    QueryProductRestModel queryProductRestModel = new QueryProductRestModel();
                    BeanUtils.copyProperties(product, queryProductRestModel);
                    return queryProductRestModel;
                }).toList());
        return list;
    }
}
