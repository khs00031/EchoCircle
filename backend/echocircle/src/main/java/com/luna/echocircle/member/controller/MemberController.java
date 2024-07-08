package com.luna.echocircle.member.controller;

import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "member", description = "회원기능 API")
@RequestMapping("/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String AUTH = "Authorization";
    MemberService memberService;

//    @Operation(summary = "회원가입", description = "User 객체를 이용해 회원가입을 하는 API")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
//            @ApiResponse(responseCode = "500", description = "회원가입 실패 - 내부 서버 오류"),
//    })
//    @PostMapping("/regist")
//    public ResponseEntity<?> regist(@RequestBody RequestRegistDto requestRegistDto) {
//        try {
//            userService.regist(requestRegistDto.getEmail(), requestRegistDto.getPassword(), requestRegistDto.getName(), requestRegistDto.getNickname());
//            log.info("회원가입 성공");
//
//            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//        } catch (Exception e) {
//            log.info("회원가입 실패 - 서버(DB) 오류");
//            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @GetMapping("/{email}")
    @Operation(summary = "유저 정보 가져오기", description = "해당 email의 유저를 가져옴")
    public ResponseEntity<?> getUserInfo(@PathVariable String email) throws Exception {
        log.info("emailController 호출 - 유저 가져오기: " + email);

        try {
            Map<String, Object> returnData = new HashMap<>();
            Member member = memberService.getMember(email);
//            List<Member> members = memberService.getAllMembers();
            log.info("멤버 검색 결과: " + member);
            returnData.put("info", member);
            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("닉네임 중복 체크 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
