package com.luna.echocircle.board.repository;//package com.luna.echocircle.Member.repository;

import com.luna.echocircle.board.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> { //JpaRepository<Entity클래스, PK타입>

    Page<Article> findAll(Pageable pageable);
//    Article findArticleById(Long aid);
//    List<Article> findAllByMemberId(long mid);

}
