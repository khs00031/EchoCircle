package com.luna.echocircle.member.service;//package com.luna.echocircle.member.service;

import com.luna.echocircle.member.dto.RequestRegistDto;
import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Member getMember(long mid){
        return memberRepository.findMemberById(mid);
    }

    public Member regist(RequestRegistDto requestRegistDto) {
        log.info("회원가입 서비스 호출 - ");
        Member member = Member.builder()
                .email(requestRegistDto.getEmail())
                .nickname(requestRegistDto.getNickname())
                .address(requestRegistDto.getAddress())
                .phone(requestRegistDto.getPhone())
                .build();

        return memberRepository.save(member);
    }

    public Boolean existNickname(String nickname) {
        log.info("회원 서비스 - 닉네임 중복 체크");
        Member member = memberRepository.findMemberByNickname(nickname);
        if(member ==null)
            return false;
        return true;
    }

    public Boolean existEmail(String email) {
        log.info("회원 서비스 - 이메일 중복 체크");
        Member member = memberRepository.findMemberByEmail(email);
        if(member ==null)
            return false;
        return true;
    }

}
