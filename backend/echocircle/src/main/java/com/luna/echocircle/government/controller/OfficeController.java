package com.luna.echocircle.government.controller;

import com.luna.echocircle.government.entity.Office;
import com.luna.echocircle.government.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@RestController
@Tag(name = "public", description = "공공기관 정보 API")
@RequestMapping("/office")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
public class OfficeController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String AUTH = "Authorization";
    OfficeService officeService;

    @GetMapping()
    @Operation(summary = "공공기관 정보 가져오기", description = "모든 공공기관 정보를 가져옴")
    public ResponseEntity<?> getGovernment() throws Exception {
        log.info("publicController 호출 - 공공기관 가져오기: " );

        try {
            Map<String, Object> returnData = new HashMap<>();

            List<Office> offices = officeService.getAllOffices();
            log.info("공공기관 리스트: " + offices);
            returnData.put("info", offices);
            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("닉네임 중복 체크 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/do")
    @Operation(summary = "도 정보 불러오기", description = "도 단위 지역정보를 불러온다.")
    public ResponseEntity<?> getDoInfo() throws Exception {

        try {
            Map<String, Object> returnData = new HashMap<>();
            List<String> doInfo = officeService.getAllDoInfo();
            returnData.put("doInfo", doInfo);
            log.info("도 정보 불러오기: " + doInfo);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("도 정보 불러오기 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/si/{doStr}")
    @Operation(summary = "시 정보 불러오기", description = "시 단위 지역정보를 불러온다.")
    public ResponseEntity<?> getSiInfo(@PathVariable String doStr) throws Exception {

        try {
            Map<String, Object> returnData = new HashMap<>();
            List<String> siInfo = officeService.getAllSiInfo(doStr);
            returnData.put("siInfo", siInfo);
            log.info("시 정보 불러오기: " + siInfo);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("시 정보 불러오기 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gu/{si}")
    @Operation(summary = "구 정보 불러오기", description = "구 단위 지역정보를 불러온다.")
    public ResponseEntity<?> getGuInfo(@PathVariable String si) throws Exception {

        try {
            Map<String, Object> returnData = new HashMap<>();
            List<String> guInfo = officeService.getAllGuInfo(si);
            returnData.put("guInfo", guInfo);
            log.info("구 정보 불러오기: " + guInfo);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("구 정보 불러오기 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dong/{si}/{gu}")
    @Operation(summary = "동 정보 불러오기", description = "동 단위 지역정보를 불러온다.")
    public ResponseEntity<?> getDongInfo(@PathVariable String si, @PathVariable String gu) throws Exception {

        try {
            Map<String, Object> returnData = new HashMap<>();
            List<String> dongInfo = officeService.getAllDongInfo(si, gu);
            returnData.put("dongInfo", dongInfo);
            log.info("동 정보 불러오기: " + dongInfo);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("동 정보 불러오기 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}