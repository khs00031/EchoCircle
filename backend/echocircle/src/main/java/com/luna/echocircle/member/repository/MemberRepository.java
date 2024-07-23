package com.luna.echocircle.member.repository;//package com.luna.echocircle.Member.repository;

import com.luna.echocircle.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> { //JpaRepository<Entity클래스, PK타입>

    Member findMemberByEmail(String email);

//    Member findMemberById(long MemberId);

    Member findMemberByNickname(String nickname);

    Member findByEmail(String email);

    Member findMemberById(long id);

}
