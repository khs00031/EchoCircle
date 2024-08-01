package com.example.echocircleandroid.ui.theme.screens.components.HomeCollect

import BottomNavItem
import NavItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
@Preview(showBackground = true)
fun CheckDeviceScreenPreview(){
    CheckDeviceScreen(rememberNavController())
}

@Composable
fun CheckDeviceScreen(navController: NavController){
    var collectable by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "아래 모델이 맞나요?")
        Text(text = " 사진 나오고")
        Text(text = " 여기에 모델 나오고")

        Row {
            Button(onClick = {
                collectable = isCollectable()

                if(collectable){
                    navController.navigate(NavItem.FoundDeviceScreen.screen_route)
                }else{
                    navController.navigate(NavItem.CannotCollectDeviceScreen.screen_route)
                }

            }) {
                Text(text = "네")
            }
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text =  "아니오")
            }
        }

    }
}

/* ToDo : 서버에 디바이스 수거 가능 한지 요청 코드 작성 */
private fun isCollectable():Boolean{
    return true;
}