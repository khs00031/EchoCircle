package com.luna.echocircle.board.controller;

import com.luna.echocircle.board.dto.RequestWriteArticleDto;
import com.luna.echocircle.board.dto.ResponseArticleDto;
import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.board.service.ArticleService;
import com.luna.echocircle.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "board", description = "게시판 API")
@RequestMapping("/board")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Slf4j
public class BoardController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    ArticleService articleService;
    MemberService memberService;

    // 글 목록 조회
    @GetMapping
    @Operation(summary = "글 목록 조회", description = "Board의 있는 모든 Article을 가져온다")
    public ResponseEntity<Map<String,Object>> loadAllArticles(Pageable pageable){
        Map<String,Object> response = new HashMap<>();
        try{
            Page<Article> allArticles = articleService.getAllArticles(pageable);
            List<ResponseArticleDto> responseArticles = new ArrayList<>();

            for(Article article : allArticles){
                ResponseArticleDto responseArticleDto = ResponseArticleDto.builder()
                        .aid(article.getAid())
                        .nickname(article.getMember().getNickname())
                        .category(article.getCategory())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .registTime(article.getRegisterTime())
                        .image(article.getImage())
                        .shared(article.isShared())
                        .view(article.getView())
                        .deleted(article.isDeleted())
                        .build();
                responseArticles.add(responseArticleDto);
            }
            response.put("articles",responseArticles);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "글 작성", description = "글 작성하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "글 작성 성공"),
            @ApiResponse(responseCode = "500", description = "글 작성 실패 - 내부 서버 오류"),
    })
    @PostMapping("/write")
    public ResponseEntity<?> writeArticle(@RequestBody RequestWriteArticleDto requestWriteArticleDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            articleService.write(requestWriteArticleDto);
            log.info("글 작성 성공");
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "글 작성 성공");
            resultMap.put("httpStatus", status);
        } catch (Exception e) {
            log.error("글 작성 실패 - " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", e.getMessage());
            resultMap.put("httpStatus", status);
        }
        return new ResponseEntity<>(resultMap, status);

    }
}
