package com.example.echocircleandroid.ui.theme.screens.components.Product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FoundDeviceScreen(navController: NavHostController){
    Column {
        Text(text = "기업 수거 가능한 제품이에요.\n 바로 수거하시겠어요?")

        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "네. 수거해주세요.")
            }
            Button(onClick = { navController.popBackStack()}) {
                Text(text = "아니요. 괜찮아요.")
            }
        }
    }

}