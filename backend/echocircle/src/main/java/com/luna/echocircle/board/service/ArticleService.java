package com.luna.echocircle.board.service;

import com.luna.echocircle.board.S3Image.S3Uploader;
import com.luna.echocircle.board.dto.RequestWriteArticleDto;
import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.board.repository.ArticleRepository;
import com.luna.echocircle.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    // Optional로 반환하도록 수정된 getArticle 메소드
    public Optional<Article> getArticle(long aid) {
        Optional<Article> article = articleRepository.findById(aid);
        if(article.isPresent()){
            article.get().setView(article.get().getView()+1);
            articleRepository.save(article.get());
        }
        return article;
    }

    public Article write(RequestWriteArticleDto requestWriteArticleDto, MultipartFile thumbnail, MultipartFile[] images) {
        log.info("write 서비스 호출 - ");
        String thumbnailURL = "";

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
        articleRepository.save(article);
        try {
            thumbnailURL = s3Uploader.upload(thumbnail, s3Uploader.ECHO + "/"+article.getAid());
            s3Uploader.uploads(images, s3Uploader.ECHO + "/"+ article.getAid() +"/"+ s3Uploader.IMAGES);
            article.setThumbnail(thumbnailURL);
        } catch (Exception e) {
            log.info("이미지 업로드 실패 : " + e.getMessage());
        }


        return articleRepository.save(article);
    }


}
