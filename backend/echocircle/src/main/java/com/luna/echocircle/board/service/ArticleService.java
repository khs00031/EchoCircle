package com.luna.echocircle.board.service;

import com.luna.echocircle.board.dto.RequestWriteArticleDto;
import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.board.repository.ArticleRepository;
import com.luna.echocircle.member.repository.MemberRepository;
import com.luna.echocircle.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }



    public Article write(RequestWriteArticleDto requestWriteArticleDto) {
        log.info("write 서비스 호출 - ");
        Article article = Article.builder()
                .member(memberService.getMember(requestWriteArticleDto.getMid()))
                .category(requestWriteArticleDto.getCategory())
                .title(requestWriteArticleDto.getTitle())
                .content(requestWriteArticleDto.getContent())
                .registerTime(LocalDateTime.now())
                .shared(requestWriteArticleDto.isShared())
                .view(0)
                .deleted(false)
                .build();

        return articleRepository.save(article);
    }
    
    
}
