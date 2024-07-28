package com.luna.echocircle.board.entity;

import com.luna.echocircle.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "article")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Long aid;

    @OneToOne
    @JoinColumn(name = "mid", nullable = false)  // `nullable = false` 추가
    @NotNull  // `@NotNull` 어노테이션 추가
    private Member member;

    @Column(name = "category")
    private int category;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "registTime")
    private LocalDateTime registerTime;

    @Column(name = "image")
    private String image;

    @Column(name = "shared")
    private boolean shared;

    @Column(name = "views")
    private int view;

    @Column(name = "deleted")
    private boolean deleted;

//    @OneToMany(mappedBy = "article")
//    private List<Comments> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "article")
//    private List<Likes> likes = new ArrayList<>();
    // getters, setters, etc.

//    // 양방향 맵핑
//    public void setUser(Member member) {
//        if(member!=null){
//            member.getArticles().remove(this);
//        }
//        this.member = member;
//        assert member != null;
//        member.getArticles().add(this);
//    }
}
