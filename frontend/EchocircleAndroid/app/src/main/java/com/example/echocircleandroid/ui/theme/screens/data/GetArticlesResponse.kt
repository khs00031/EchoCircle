package com.example.echocircleandroid.ui.theme.screens.data

data class GetArticlesResponse(
    val httpStatus: String,
    val message: String,
    val articles: List<Article>
)