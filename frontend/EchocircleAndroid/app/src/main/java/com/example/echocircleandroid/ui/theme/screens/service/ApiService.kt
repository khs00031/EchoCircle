package com.example.echocircleandroid.ui.theme.screens.service

import com.example.echocircleandroid.ui.theme.screens.data.LoginRequest
import com.example.echocircleandroid.ui.theme.screens.data.LoginResponse
import com.example.echocircleandroid.ui.theme.screens.data.NicknameResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // 로그인 요청을 위한 POST 메서드 정의
    @POST("/api/member/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

     //주석 처리된 닉네임 가져오기 메서드 예시 (추가 구현 가능)
     @GET("/api/member/mypage")
     suspend fun getNickname(): NicknameResponse
}
