package com.example.echocircleandroid.ui.theme.screens.components.Member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance // RetrofitInstance 가져오기
import com.example.echocircleandroid.ui.theme.screens.data.RegistRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class RegistResponse(val httpStatus: String, val message: String)

class MemberRegistViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _registResponse = MutableStateFlow<RegistResponse?>(null)
    val registResponse: StateFlow<RegistResponse?> = _registResponse

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNicknameChange(newNickname: String) {
        _nickname.value = newNickname
    }

    fun onAddressChange(newAddress: String) {
        _address.value = newAddress
    }

    fun onPhoneChange(newPhone: String) {
        _phone.value = newPhone
    }

    suspend fun checkEmailDuplicate(email: String): Boolean {
        return try {
            // 이메일 중복 확인을 위한 네트워크 요청 처리
            val response = RetrofitInstance.api.checkEmailDuplicate(email)
            response.exist
        } catch (e: IOException) {
            false // 네트워크 오류 시 중복되지 않음으로 간주
        } catch (e: HttpException) {
            false // 서버 오류 시 중복되지 않음으로 간주
        }
    }

    suspend fun checkNicknameDuplicate(nickname: String): Boolean {
        return try {
            // 닉네임 중복 확인을 위한 네트워크 요청 처리
            val response = RetrofitInstance.api.checkNicknameDuplicate(nickname)
            response.exist
        } catch (e: IOException) {
            false // 네트워크 오류 시 중복되지 않음으로 간주
        } catch (e: HttpException) {
            false // 서버 오류 시 중복되지 않음으로 간주
        }
    }

    fun registMember() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.registMember(
                    RegistRequest(
                        email = email.value,
                        pw = password.value,
                        nickname = nickname.value,
                        address = address.value,
                        phone = phone.value
                    )
                )
                _registResponse.value = RegistResponse(response.httpStatus, response.message)
            } catch (e: IOException) {
                _registResponse.value = RegistResponse("ERROR", "네트워크 오류 발생")
            } catch (e: HttpException) {
                _registResponse.value = RegistResponse("ERROR", "서버 오류 발생")
            }
        }
    }
}
