package com.luna.echocircle.product.service;


import com.luna.echocircle.S3Image.S3Uploader;
import com.luna.echocircle.product.entity.Product;
import com.luna.echocircle.product.dto.RequestRegistProductDto;
import com.luna.echocircle.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
                .category(requestRegistProductDto.getCategory())
                .company(requestRegistProductDto.getCompany())
                .model(requestRegistProductDto.getModel())
                .serial(requestRegistProductDto.getSerial())
                .build();
        try {
            String url = s3Uploader.upload(image, s3Uploader.ECHO + "/" + s3Uploader.PRODUCT);
            product.setImage(url);
        } catch (Exception e) {
            log.info("제품 등록 실패 : " + e.getMessage());
        }
        return productRepository.save(product);
    }

    public List<Product> getProductByCode(String code) throws NoSuchElementException {
        List<Product> productList = productRepository.findByModel(code);
        List<Product> tempList = productRepository.findBySerial(code);
        if (productList.isEmpty() && tempList.isEmpty())
            throw new NoSuchElementException("존재하지 않는 제품code");
        for(int i=0;i<tempList.size();i++)
            productList.add(tempList.get(i));
        return productList;
    }


}