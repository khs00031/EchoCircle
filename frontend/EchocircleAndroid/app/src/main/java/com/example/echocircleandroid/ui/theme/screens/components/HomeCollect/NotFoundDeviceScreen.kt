package com.example.echocircleandroid.ui.theme.screens.components.HomeCollect

import BottomNavItem
import NavItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@Composable
fun NotFoundDeviceScreen(navController: NavController){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "찾으시는 모델이 없습니다. \n 지자체 연결을 도와드릴까요?")

        Row {
            Button(onClick = { navController.navigate(NavItem.PhoneCallCollectScreen.screen_route) }) {
                Text("네.")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "아니오")
            }
        }
    }
}