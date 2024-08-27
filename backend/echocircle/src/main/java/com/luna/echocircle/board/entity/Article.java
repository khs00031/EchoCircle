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

    @ManyToOne
    @JoinColumn(name = "mid", nullable = false)  // `nullable = false` 추가
    @NotNull  // `@NotNull` 어노테이션 추가
    private Member member;

    @Column(name = "category")
    private String category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "registTime", nullable = false)
    private LocalDateTime registerTime;

    @Column(name = "thumbnail")
    private String thumbnail;

//    @Column(name = "imagesDir")
//    private String imagesDir;

    @Column(name = "shared", nullable = false)
    private boolean shared;

    @Column(name = "views", nullable = false)
    private int view;

    @Column(name = "deleted", nullable = false)
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
