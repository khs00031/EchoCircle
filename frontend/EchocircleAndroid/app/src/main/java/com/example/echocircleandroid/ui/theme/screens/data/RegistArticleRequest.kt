package com.example.echocircleandroid.ui.theme.screens.data

import android.net.Uri

data class RegistArticleRequest(
    val requestWriteArticleDto: RequestWriteArticleDto,
    val thumbnail: Uri?,
    val images: Uri?
)

data class RequestWriteArticleDto(
    val email: String,
    val category: Int,
    val title: String,
    val content: String,
    val shared: Boolean
)