package com.example.echocircleandroid.ui.theme.screens.components.MyPage

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.Article
import com.example.echocircleandroid.ui.theme.screens.data.MyPageResponse
import com.example.echocircleandroid.ui.theme.screens.data.MypageRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MyPageViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading by mutableStateOf(false)

    private val context: Context get() = getApplication<Application>().applicationContext

    private val _nickname = mutableStateOf("Loading...")
    val nickname: State<String> = _nickname

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    private val _mypageResponse = MutableStateFlow<MyPageResponse?>(null)
    val myPageResponse: StateFlow<MyPageResponse?> = _mypageResponse

    private val _articleList = MutableStateFlow<List<Article>>(emptyList())
    val articleList: StateFlow<List<Article>> = _articleList

    init {
        fetchNickname()
    }

    private fun fetchNickname() {
        viewModelScope.launch {
//            _email.value = SharedPreferencesUtil.getUserEmail(context).toString()
//            _token.value = SharedPreferencesUtil.getAuthToken(context).toString()
            _email.value = "aaa@aaa"
            _token.value = "ol65"

            try {
                val mypageRequest = MypageRequest(_email.value, _token.value)
                val response = RetrofitInstance.api.getNickname(mypageRequest)
                _mypageResponse.value = response

                if (response.mypage != null) {
                    _nickname.value = response.mypage?.member?.nickname.toString()
                    if (response.mypage.articleList.isNotEmpty()) {
                        _articleList.value = response.mypage.articleList
                    }
                }
                isLoading = true
            } catch (e: HttpException) {
                // 서버에서 반환된 에러 메시지를 읽어오기
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                _mypageResponse.value = MyPageResponse(
                    httpStatus = "UNAUTHORIZED",
                    message = errorMessage,
                    mypage = null
                )
            } catch (e: IOException) {
                _mypageResponse.value = MyPageResponse(
                    httpStatus = "INTERNAL_SERVER_ERROR",
                    message = "서버 내부 오류",
                    mypage = null
                )
            }
        }
    }

    // Helper function to parse error message from JSON
    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
            jsonObject.get("message")?.asString ?: "알 수 없는 오류"
        } catch (e: Exception) {
            "알 수 없는 오류"
        }
    }
}