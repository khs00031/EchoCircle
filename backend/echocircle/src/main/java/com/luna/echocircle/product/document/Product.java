package com.luna.echocircle.product.document;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


@Data
@Builder
public class Product {
    @Id
    private ObjectId id;

    private String name;
    private String barcode;
}