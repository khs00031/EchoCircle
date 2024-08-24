package com.luna.echocircle.board.repository;//package com.luna.echocircle.Member.repository;

import com.luna.echocircle.board.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> { //JpaRepository<Entity클래스, PK타입>

    Page<Article> findAll(Pageable pageable);
    Optional<Article> findById(Long aid);
//    List<Article> findAllByMemberId(long mid);

    List<Article> findByMember_Id(Long id); // 여기서 'mid'를 'id'로 수정
}
