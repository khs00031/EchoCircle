package com.luna.echocircle.member.dto;

import com.luna.echocircle.board.entity.Article;
import com.luna.echocircle.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMypageDto {
    Member member;
    List<Article> articleList;
}

