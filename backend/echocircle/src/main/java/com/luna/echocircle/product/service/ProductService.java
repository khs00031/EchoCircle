package com.luna.echocircle.product.service;


import com.luna.echocircle.S3Image.S3Uploader;
import com.luna.echocircle.product.document.Product;
import com.luna.echocircle.product.dto.RequestRegistProductDto;
import com.luna.echocircle.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;

    public Product addProduct(RequestRegistProductDto requestRegistProductDto, MultipartFile image) {
        log.info("제품 등록 서비스 호출 - ");
        Product product = Product.builder()
                .name(requestRegistProductDto.getName())
                .barcode(requestRegistProductDto.getBarcode())
                .build();
        try {
            String url = s3Uploader.upload(image, s3Uploader.ECHO + "/" + s3Uploader.PRODUCT);
            product.setImage(url);
        } catch (Exception e) {
            log.info("제품 등록 실패 : " + e.getMessage());
        }
        return productRepository.save(product);
    }

    public Product getProductByBarcode(String barcode) throws NoSuchElementException {
        Optional<Product> opProduct = productRepository.findByBarcode(barcode);
        if (opProduct.isEmpty())
            throw new NoSuchElementException("존재하지 않는 barcode");
        return opProduct.get();
    }


}