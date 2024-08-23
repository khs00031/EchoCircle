package com.luna.echocircle.product.controller;


import com.luna.echocircle.product.document.Product;
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
import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Operation(summary = "제품 불러오기", description = "입력한 barcode의 Product 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "불러오기 성공"),
            @ApiResponse(responseCode = "500", description = "불러오기 실패 - 내부 서버 오류"),
    })
    @GetMapping("/{barcode}")
    public ResponseEntity<?> getUserInfo(@PathVariable String barcode) throws Exception {
        log.info("productController 호출 - 제품 가져오기: " + barcode);

        try {
            Map<String, Object> returnData = new HashMap<>();
            Product product = productService.getProductByBarcode(barcode);
            log.info("제품 검색 결과: " + product);
            returnData.put("info", product);
            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("제품 가져오기 실패 : " + e.getMessage());
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
