package com.example.echocircleandroid

import MainApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.echocircleandroid.ui.theme.EchocircleAndroidTheme
import com.example.echocircleandroid.ui.theme.screens.data.SharedPreferencesUtil


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EchocircleAndroidTheme {
                val startDestination = if (SharedPreferencesUtil.getAuthToken(this) != null) {
                    "direct_processing" // 로그인 상태인 경우 홈 화면으로 시작
                } else {
                    "login" // 로그인되지 않은 경우 로그인 화면으로 시작
                }
                MainApp(startDestination)
            }
        }
    }
}
