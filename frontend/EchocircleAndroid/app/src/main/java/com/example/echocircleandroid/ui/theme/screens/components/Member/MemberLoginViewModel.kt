package com.example.echocircleandroid.ui.theme.screens.components.Member

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.LoginRequest
import com.example.echocircleandroid.ui.theme.screens.data.LoginResponse
import com.example.echocircleandroid.ui.theme.screens.data.SharedPreferencesUtil
import com.example.echocircleandroid.ui.theme.screens.service.ApiService
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MemberLoginViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiService = RetrofitInstance.api
    private val context: Context get() = getApplication<Application>().applicationContext

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email.value, password.value)
                val response = apiService.login(loginRequest)
                _loginResponse.value = response

                if (response.httpStatus == "ACCEPTED") {
                    SharedPreferencesUtil.saveAuthToken(context, response.token ?: "")
                    SharedPreferencesUtil.saveUserEmail(context, email.value)
                }
            } catch (e: HttpException) {
                // 서버에서 반환된 에러 메시지를 읽어오기
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                _loginResponse.value = LoginResponse(
                    httpStatus = "UNAUTHORIZED",
                    message = errorMessage
                )
            } catch (e: IOException) {
                _loginResponse.value = LoginResponse(
                    httpStatus = "INTERNAL_SERVER_ERROR",
                    message = "서버 연결 실패"
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
