package com.luna.echocircle.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAllArticleDto {
    long aid;    // article id
    String nickname;    // 작성자 nickname
    String category;    // 카테고리id
    String title;   // 제목
    String content;    // 내용
    LocalDateTime registTime; // 등록시간
    String thumbnail;     // 썸네일
    boolean shared; // 나눔상태
    int view;  // 조회수
    boolean deleted;
}
