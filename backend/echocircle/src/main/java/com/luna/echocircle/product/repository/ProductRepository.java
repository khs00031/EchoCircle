package com.luna.echocircle.product.repository;

import com.luna.echocircle.product.document.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    Optional<Product> findById(ObjectId id);
    Optional<Product> findByBarcode(String barcode);
}