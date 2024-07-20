package com.luna.echocircle.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestRegistProductDto {
    String name;
    String barcode;
}
