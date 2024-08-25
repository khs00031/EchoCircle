package com.luna.echocircle.member.service;

import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.board.repository.ArticleRepository;
import com.luna.echocircle.member.dto.RequestLoginDto;
import com.luna.echocircle.member.dto.RequestMyPageDto;
import com.luna.echocircle.member.dto.RequestRegistDto;
import com.luna.echocircle.member.dto.ResponseMypageDto;
import com.luna.echocircle.member.entity.Member;
import com.luna.echocircle.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

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

        // Member 객체 생성
        Member member = Member.builder()
                .email(requestRegistDto.getEmail())
                .nickname(requestRegistDto.getNickname())
                .address(requestRegistDto.getAddress())
                .phone(requestRegistDto.getPhone())
                .pw(requestRegistDto.getPw())
                .build();

        // Member 객체 저장
        return memberRepository.save(member);
    }

    public String login(RequestLoginDto requestLoginDto) throws Exception {
        log.info("로그인 서비스 호출 - ");
        Member member = memberRepository.findMemberByEmail(requestLoginDto.getEmail());
        if (member == null)
            throw new InvalidCredentialsException("존재하지 않는 Email");
        if (!member.getPw().equals(requestLoginDto.getPw()))
            throw new InvalidCredentialsException("비밀번호 틀림");
        String token = generateToken();
        member.setToken(token);
        memberRepository.save(member);
        return token;
    }

    public String generateToken() {
        Random random = new Random();
        String token = "";
        for (int i = 0; i < 2; i++)
            token += (char)('a' + random.nextInt(26));
        for (int i = 0; i < 2; i++)
            token += random.nextInt(10);
        return token;
    }

    public ResponseMypageDto loadMypage(RequestMyPageDto requestMyPageDto) throws Exception{
        Member member = memberRepository.findMemberByEmail(requestMyPageDto.getEmail());
        if(!member.getToken().equals(requestMyPageDto.getToken()))
            throw new Exception("토큰 만료");

        List<Article> articleList = articleRepository.findByMember_Id(member.getId());
        ResponseMypageDto responseMypageDto = ResponseMypageDto.builder()
                .member(member)
                .articleList(articleList)
                .build();

        return responseMypageDto;
    }

    public boolean logout(RequestMyPageDto requestMyPageDto){
        Member member = memberRepository.findMemberByEmail(requestMyPageDto.getEmail());
        member.setToken("");
        memberRepository.save(member);
        return true;
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
