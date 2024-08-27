package com.example.echocircleandroid.ui.theme.screens.data

data class MyPageResponse (
    val httpStatus: String,
    val mypage: Mypage?,
    val message: String
)

data class Mypage(
    val member: Member,
    val articleList: List<Article>
)

data class Member(
    val id: Int,
    val email: String,
    val pw: String,
    val token: String,
    val nickname: String,
    val address: String,
    val phone: String
)

data class Article(
    val aid: Int,
    val nickname: String,
    val category: String,
    val title: String,
    val content: String,
    val registTime: String,
    val thumbnail: String?,
    val images: List<String>,
    val shared: Boolean,
    val view: Int,
    val deleted: Boolean
)