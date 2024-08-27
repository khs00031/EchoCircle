package com.luna.echocircle.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "company")
    private String company;

    @Column(name = "size")
    private int size;

    // 생산연도
    @Column(name = "year")
    private int year;

    @Column(name = "model")
    private String model;

    @Column(name = "serial")
    private String serial;

    @Column(name = "image")
    private String image;


}
