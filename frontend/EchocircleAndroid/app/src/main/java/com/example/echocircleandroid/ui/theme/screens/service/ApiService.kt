package com.example.echocircleandroid.ui.theme.screens.service

import com.example.echocircleandroid.ui.theme.screens.data.LoginRequest
import com.example.echocircleandroid.ui.theme.screens.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.echocircleandroid.ui.theme.screens.data.MyPageResponse
import com.example.echocircleandroid.ui.theme.screens.data.MypageRequest

interface ApiService {

    // 로그인 요청을 위한 POST 메서드 정의
    @POST("/api/member/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

     //주석 처리된 닉네임 가져오기 메서드 예시 (추가 구현 가능)
     @POST("/api/member/mypage")
     suspend fun getNickname(@Body request: MypageRequest): MyPageResponse
}
