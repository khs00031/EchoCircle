package com.luna.echocircle.product.repository;

import com.luna.echocircle.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findBySerial(String serial);

    List<Product> findByModel(String model);
}
