package com.example.echocircleandroid.ui.theme.screens.data

data class GetArticleResponse(
    val httpStatus: String,
    val message: String,
    val article: Article?
)
