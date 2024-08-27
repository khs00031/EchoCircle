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
                .size(requestRegistProductDto.getSize())
                .year(requestRegistProductDto.getYear())
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

    public Product getProductByCode(String code) throws NoSuchElementException {
        Product product = productRepository.findByModel(code);
        Product temp = productRepository.findBySerial(code);
        if (product ==null && temp ==null)
            throw new NoSuchElementException("존재하지 않는 제품code: "+code);
        if(product ==null)
            return temp;
        return product;
    }

    // 기업 수거 가능여부, 추후 확장 가능
    public boolean canCompanyCollect(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            throw new NoSuchElementException("해당 가전제품 존재하지 않음");

        // 2015년 이후 출시된 가전만 수거가능
        if (product.get().getYear() >= 2015)
            return true;
        return false;
    }

    // 무상 방문수거 서비스 이용가능 여부
    public boolean canVisitCollect(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            throw new NoSuchElementException("해당 가전제품 존재하지 않음");

        // 중형가전 이상만 수거가능
        if (product.get().getSize() >= 2)
            return true;
        return false;
    }


}