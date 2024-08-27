package com.luna.echocircle.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCollectableDto {
    String category;
    String company;
    int size;
    int year;
}

