package com.example.echocircleandroid.ui.theme.screens.service

import com.example.echocircleandroid.ui.theme.screens.data.LoginRequest
import com.example.echocircleandroid.ui.theme.screens.data.LoginResponse
import com.example.echocircleandroid.ui.theme.screens.data.MyPageResponse
import com.example.echocircleandroid.ui.theme.screens.data.MypageRequest
import com.example.echocircleandroid.ui.theme.screens.data.RegistRequest
import com.example.echocircleandroid.ui.theme.screens.data.RegistResponse
import com.example.echocircleandroid.ui.theme.screens.data.CheckDuplicateResponse
import com.example.echocircleandroid.ui.theme.screens.data.ProductResponse
import com.example.echocircleandroid.ui.theme.screens.data.GetArticleResponse
import com.example.echocircleandroid.ui.theme.screens.data.GetArticlesResponse
import com.example.echocircleandroid.ui.theme.screens.data.RegistArticleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // 로그인 요청을 위한 POST 메서드 정의
    @POST("/api/member/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // 로그아웃 요청을 위한 POST 메서드 정의
    @POST("/api/member/logout")
    suspend fun logout(@Body request: MypageRequest): MyPageResponse

    // 주석 처리된 닉네임 가져오기 메서드 예시 (추가 구현 가능)
    @POST("/api/member/mypage")
    suspend fun getNickname(@Body request: MypageRequest): MyPageResponse

    // 회원가입 요청을 위한 POST 메서드 정의
    @POST("/api/member/regist")
    suspend fun registMember(@Body request: RegistRequest): RegistResponse

    // 이메일 중복 확인을 위한 GET 메서드 정의
    @GET("/api/member/checkEmail/{email}")
    suspend fun checkEmailDuplicate(@Path("email") email: String): CheckDuplicateResponse

    // 닉네임 중복 확인을 위한 GET 메서드 정의
    @GET("/api/member/checkNickname/{nickname}")
    suspend fun checkNicknameDuplicate(@Path("nickname") nickname: String): CheckDuplicateResponse

    @GET("/api/product/{serialNumber}")
    suspend fun getProduct(@Path("serialNumber") serialNumber: String): ProductResponse


    // 글 등록 메서드 정의
//    @POST("/api/board/write")
//    suspend fun registArticle(@Body request: RegistArticleRequest): RegistArticleResponse

//    @Multipart
//    @POST("/api/board/write")
//    suspend fun registArticle(
//        @Part("email") email: String,
//        @Part("category") category: Int,
//        @Part("title") title: String,
//        @Part("content") content: String,
//        @Part("shared") shared: Boolean,
//        @Part thumbnail: MultipartBody.Part?,
//        @Part images: List<MultipartBody.Part>?
//    ): RegistArticleResponse

    @Multipart
    @POST("/api/board/write")
    suspend fun registArticle(
        @Part("requestWriteArticleDto") request: RequestBody,
        @Part thumbnail: MultipartBody.Part?,
        @Part images: List<MultipartBody.Part>?
    ): RegistArticleResponse

    // 모든 글 조회
    @GET("/api/board")
    suspend fun getArticles(): GetArticlesResponse

    // 글 상세 조회
    @GET("/api/board/{articleId}")
    suspend fun getArticle(
        @Path("articleId") articleId: Int
    ): GetArticleResponse
}
