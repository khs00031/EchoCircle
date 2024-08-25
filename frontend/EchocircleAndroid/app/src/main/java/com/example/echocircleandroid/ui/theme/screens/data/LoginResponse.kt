package com.example.echocircleandroid.ui.theme.screens.data

data class LoginResponse(
    val httpStatus: String,
    val message: String,
    val token: String? = null  // 로그인 성공 시에만 존재하는 필드
)
