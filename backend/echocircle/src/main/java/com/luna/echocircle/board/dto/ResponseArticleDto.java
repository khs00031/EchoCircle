package com.luna.echocircle.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseArticleDto {
    long aid;    // article id
    String nickname;    // 작성자 nickname
    int category;    // 카테고리id
    String title;   // 제목
    String content;    // 내용
    LocalDateTime registTime; // 등록시간
    String thumbnail;     // 썸네일
    List<String> images;    // 이미지들
    boolean shared; // 나눔상태
    int view;  // 조회수
    boolean deleted;
}
