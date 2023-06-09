package com.ahmet.product.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookup, String> {
    Optional<ProductLookup> findByProductIdOrTitle(String productId, String title);
}
