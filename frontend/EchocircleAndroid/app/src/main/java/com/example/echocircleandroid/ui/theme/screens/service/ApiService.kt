package com.example.echocircleandroid.ui.theme.screens.service
import com.example.echocircleandroid.ui.theme.screens.data.NicknameResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/api/member/mypage")
    suspend fun getNickname(): NicknameResponse
}