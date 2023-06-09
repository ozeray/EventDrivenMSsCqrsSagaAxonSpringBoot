package com.ahmet.product.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productlookup")
public class ProductLookup implements Serializable {

    private static final long serialVersionUID = 23456787433458L;

    @Id
    private String productId;

    @Column(unique = true)
    private String title;

}
