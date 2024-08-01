package com.example.echocircleandroid.ui.theme.screens.components.HomeCollect

import BottomNavItem
import NavItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CannotCollectDeviceScreen(navController: NavController){
    Column (
        
    ){
        Text(text = "기업 수거 불가능 제품입니다. \n 관련 지자체 연락처를 안내해 드릴까요?")
        
        Row {
            Button(onClick = { navController.navigate(NavItem.PhoneCallCollectScreen.screen_route) }) {
                Text(text = "네. 연결해주세요.")
            }
            Button(onClick = { navController.popBackStack()}) {
                Text(text = "아니요. 괜찮아요.")
            }
        }
    }
}