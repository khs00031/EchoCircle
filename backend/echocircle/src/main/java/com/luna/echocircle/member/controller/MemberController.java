package com.luna.echocircle.member.controller;

import com.luna.echocircle.member.dto.RequestRegistDto;
import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.service.MemberService;
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

    @Operation(summary = "회원가입", description = "Member 객체를 이용해 회원가입을 하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "500", description = "회원가입 실패 - 내부 서버 오류"),
    })
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody RequestRegistDto requestRegistDto) {
        try {
            log.info("회원가입 시작");
            memberService.regist(requestRegistDto.getEmail(), requestRegistDto.getNickname(), requestRegistDto.getAddress(), requestRegistDto.getPhone());
            log.info("회원가입 성공");

            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            log.info("회원가입 실패 - 서버(DB) 오류");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/checkNickname/{nickname}")
    @Operation(summary = "닉네임 중복 체크", description = "해당 닉네임이 DB에 있는지 검사한다. ")
    public ResponseEntity<?> verifyNickName(@PathVariable String nickname) throws Exception {
        log.info("memberController 호출 - 닉네임 중복 체크: " + nickname);

        try {
            Map<String, Object> returnData = new HashMap<>();
            boolean exist = memberService.checkNickname(nickname);
            returnData.put("중복 여부", exist);
            log.info("닉네임 중복 체크 결과: " + exist);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("닉네임 중복 체크 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkEmail/{email}")
    @Operation(summary = "이메일 중복 체크", description = "해당 이메일이 DB에 있는지 검사한다. ")
    public ResponseEntity<?> verifyEMail(@PathVariable String email) throws Exception {
        log.info("memberController 호출 - 이메일 중복 체크: " + email);

        try {
            Map<String, Object> returnData = new HashMap<>();
            boolean exist = memberService.checkEmail(email);
            returnData.put("중복 여부", exist);
            log.info("이메일 중복 체크 결과: " + exist);

            // 인증 코드 리턴
            return new ResponseEntity<Map<String, Object>>(returnData, HttpStatus.OK);
        } catch (Exception e) {
            log.info("이메일 중복 체크 실패");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
