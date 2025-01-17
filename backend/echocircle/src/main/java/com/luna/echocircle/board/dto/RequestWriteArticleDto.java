package com.luna.echocircle.board.dto;

import lombok.Data;


@Data
public class RequestWriteArticleDto {
    String email;    // 작성자 정보
    String category;    // 카테고리id
    String title;   // 제목
    String content;    // 내용
    boolean shared; // 나눔상태
}
