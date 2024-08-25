package com.luna.echocircle.government.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "office")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private long id;

    @Column(name = "do", nullable = false)
    private String doStr;

    @Column(name = "si", nullable = false)
    private String si;

    @Column(name = "gu", nullable = true)  // null 허용
    private String gu;

    @Column(name = "dong", nullable = true)  // null 허용
    private String dong;

    @Column(name = "phone")
    private String phone;

    @Column(name = "url")
    private String url;


}