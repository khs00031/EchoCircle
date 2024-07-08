package com.luna.echocircle.member.service;//package com.luna.echocircle.user.service;

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


}
