package com.example.echocircleandroid.ui.theme.screens.components.MyPage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {

    private val _nickname = mutableStateOf("Loading...")
    val nickname: State<String> = _nickname

    init {
        fetchNickname()
    }

    private fun fetchNickname() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNickname()
                _nickname.value = response.nickname
            } catch (e: Exception) {
                _nickname.value = "Error loading"
            }
        }
    }
}