package com.luna.echocircle.member.service;

import com.luna.echocircle.member.dto.RequestLoginDto;
import com.luna.echocircle.member.dto.RequestRegistDto;
import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder 임포트
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Member getMember(long mid) {
        return memberRepository.findMemberById(mid);
    }

    public Member regist(RequestRegistDto requestRegistDto) {
        log.info("회원가입 서비스 호출 - ");

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestRegistDto.getPw());

        // Member 객체 생성
        Member member = Member.builder()
                .email(requestRegistDto.getEmail())
                .nickname(requestRegistDto.getNickname())
                .address(requestRegistDto.getAddress())
                .phone(requestRegistDto.getPhone())
                .pw(encodedPassword) // 인코딩된 비밀번호 설정
                .build();

        // Member 객체 저장
        return memberRepository.save(member);
    }

    public String login(RequestLoginDto requestLoginDto) {
        log.info("로그인 서비스 호출 - ");
        Member member = memberRepository.findMemberByEmail(requestLoginDto.getEmail());

        // 로그인 로직 추가 필요 (비밀번호 비교 및 JWT 생성 등)
        return "";
    }

    public Boolean existNickname(String nickname) {
        log.info("회원 서비스 - 닉네임 중복 체크");
        Member member = memberRepository.findMemberByNickname(nickname);
        return member != null;
    }

    public Boolean existEmail(String email) {
        log.info("회원 서비스 - 이메일 중복 체크");
        Member member = memberRepository.findMemberByEmail(email);
        return member != null;
    }
}
