package com.luna.echocircle.member.service;//package com.luna.echocircle.member.service;

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

    public Member regist(String email, String nickname, String address, String phone) {
        log.info("회원가입 서비스 호출 - ");
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .address(address)
                .phone(phone)
                .build();

        return memberRepository.save(member);
    }

}
