package com.luna.echocircle.member.controller;

import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.member.dto.RequestLoginDto;
import com.luna.echocircle.member.dto.RequestMyPageDto;
import com.luna.echocircle.member.dto.RequestRegistDto;
import com.luna.echocircle.member.dto.ResponseMypageDto;
import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.InvalidCredentialsException;
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
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            memberService.regist(requestRegistDto);
            log.info("회원가입 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "회원가입 성공");
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.error("회원가입 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);

    }

    @Operation(summary = "로그인", description = "로그인 후 Token을 반환하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "500", description = "로그인 실패 - 내부 서버 오류"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            String token = memberService.login(requestLoginDto);
            log.info("로그인 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", "로그인 성공");
            resultMap.put("token", token);
        } catch (InvalidCredentialsException e) {
            log.error("로그인 실패 - " + e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("로그인 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("httpStatus", status);
            resultMap.put("message", e.getMessage());
        }
        return new ResponseEntity<>(resultMap, status);

    }

    @Operation(summary = "로그아웃", description = "로그아웃 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "500", description = "로그아웃 실패 - 내부 서버 오류"),
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RequestMyPageDto requestMyPageDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            memberService.logout(requestMyPageDto);
            log.info("로그아웃 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", "로그아웃 성공");
        } catch (Exception e) {
            log.error("로그아웃 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("httpStatus", status);
            resultMap.put("message", e.getMessage());
        }
        return new ResponseEntity<>(resultMap, status);

    }


    @Operation(summary = "마이페이지", description = "마이페이지 불러오는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마이페이지 로드 성공"),
            @ApiResponse(responseCode = "500", description = "마이페이지 로드 실패 - 내부 서버 오류"),
    })
    @PostMapping("/mypage")
    public ResponseEntity<?> mypage(@RequestBody RequestMyPageDto requestMyPageDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            ResponseMypageDto mypage = memberService.loadMypage(requestMyPageDto);
            log.info("마이페이지 불러오기 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "마이페이지 불러오기 성공");
            resultMap.put("mypage", mypage);
        } catch (InvalidCredentialsException e) {
            log.error("토큰만료, 마이페이지 불러오기 실패 - " + e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("마이페이지 불러오기 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);

    }

    @Operation(summary = "작성글 불러오기", description = "작성글 불러오는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "작성글 로드 성공"),
            @ApiResponse(responseCode = "500", description = "작성글 로드 실패 - 내부 서버 오류"),
    })
    @PostMapping("/mypage/article")
    public ResponseEntity<?> mypageArticles(@RequestBody RequestMyPageDto requestMyPageDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Article> articles = memberService.loadMyArticles(requestMyPageDto);
            log.info("작성글 불러오기 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", "작성글 불러오기 성공");
            resultMap.put("articles", articles);
        } catch (InvalidCredentialsException e) {
            log.error("토큰만료, 작성글 불러오기 실패 - " + e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("httpStatus", status);
            resultMap.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("마이페이지 작성글 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);

    }

    @GetMapping("/checkNickname/{nickname}")
    @Operation(summary = "닉네임 중복 체크", description = "해당 닉네임이 DB에 있는지 검사한다. ")
    public ResponseEntity<?> verifyNickName(@PathVariable String nickname) throws Exception {
        log.info("memberController 호출 - 닉네임 중복 체크: " + nickname);

        try {
            Map<String, Object> returnData = new HashMap<>();
            boolean exist = memberService.existNickname(nickname);
            returnData.put("exist", exist);
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
            boolean exist = memberService.existEmail(email);
            returnData.put("exist", exist);
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
