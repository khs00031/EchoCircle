package com.luna.echocircle.product.controller;


import com.luna.echocircle.product.dto.RequestCollectableDto;
import com.luna.echocircle.product.entity.Product;
import com.luna.echocircle.product.dto.RequestRegistProductDto;
import com.luna.echocircle.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Operation(summary = "제품 불러오기", description = "입력한 code의 Product 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "불러오기 성공"),
            @ApiResponse(responseCode = "500", description = "불러오기 실패 - 내부 서버 오류"),
    })
    @GetMapping("/{code}")
    public ResponseEntity<?> loadProduct(@PathVariable String code) throws Exception {
        log.info("productController 호출 - 제품 가져오기: " + code);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        try {
            Product product = productService.getProductByCode(code);
            log.info("제품 검색 결과: " + product);
            status = HttpStatus.ACCEPTED;
            resultMap.put("product", product);
            resultMap.put("httpStatus", status);
        } catch (NoSuchElementException e) {
            log.info("제품 가져오기 실패 : " + e.getMessage());
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.info("내부 서버 오류 : " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Operation(summary = "수거여부 판단", description = "입력한 id Product의 수거 가능여부 판단")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "불러오기 성공"),
            @ApiResponse(responseCode = "500", description = "불러오기 실패 - 내부 서버 오류"),
    })
    @GetMapping("/collectable/{id}")
    public ResponseEntity<?> collectable(@PathVariable Long id) throws Exception {
        log.info("productController 호출 - 가능한 수거방법: " + id);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        try {
            boolean campanyCollect = productService.canCompanyCollect(id);
            boolean visitCollect = productService.canVisitCollect(id);

            status = HttpStatus.ACCEPTED;
            resultMap.put("campanyCollect", campanyCollect);
            resultMap.put("visitCollect", visitCollect);
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.info("내부 서버 오류 : " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Operation(summary = "수거여부 판단", description = "선택한 제품 수거 가능여부 판단")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "불러오기 성공"),
            @ApiResponse(responseCode = "500", description = "불러오기 실패 - 내부 서버 오류"),
    })
    @PostMapping("/collectable")
    public ResponseEntity<?> collectable(@RequestBody RequestCollectableDto requestCollectableDto) throws Exception {
        log.info("productController 호출 - 가능한 수거방법: " + requestCollectableDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        try {
            boolean campanyCollect = productService.canCompanyCollect(requestCollectableDto);
            boolean visitCollect = productService.canVisitCollect(requestCollectableDto);

            status = HttpStatus.ACCEPTED;
            resultMap.put("campanyCollect", campanyCollect);
            resultMap.put("visitCollect", visitCollect);
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.info("내부 서버 오류 : " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Operation(summary = "제품등록", description = "가전제품 정보를 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "제품등록 성공"),
            @ApiResponse(responseCode = "500", description = "제품등록 실패 - 내부 서버 오류"),
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> registProduct(@RequestPart RequestRegistProductDto requestRegistProductDto,
                                           @RequestPart MultipartFile image) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            productService.addProduct(requestRegistProductDto, image);
            log.info("제품등록 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "제품등록 성공");
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.error("제품등록 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);

    }

}
