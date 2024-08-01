package com.example.echocircleandroid.ui.theme.screens.components.HomeCollect

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext



@Composable
fun PhoneCallCollectScreen() {
    val context = LocalContext.current
    var hasCallPermission by remember { mutableStateOf(false) }

    var phoneNumber by remember {
        mutableStateOf("")
    }


    Column {
        Text("쓰레기 처리 할 수 있는 지역들 여기다가 선택할 수 있게끔 하고")

        Button(onClick = { /*
        TODO : 지역 기반으로 선택된 항목의 전화번호 가져와서 연결하기. https://www.15990903.or.kr/portal/reserve/searchReserveLocation.do# 참조*/
            phoneNumber = "031-423-4471"


        }) {
            Text("연결해 주세요.")
        }

        Text(text = phoneNumber)
    }
}