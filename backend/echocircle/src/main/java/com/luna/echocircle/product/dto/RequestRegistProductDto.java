package com.luna.echocircle.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestRegistProductDto {
    String name;
    String category;
    String company;
    int size;
    int year;
    String model;
    String serial;
    String image;
}
