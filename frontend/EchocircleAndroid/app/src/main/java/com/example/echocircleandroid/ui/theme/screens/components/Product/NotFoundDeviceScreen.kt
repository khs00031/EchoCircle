package com.example.echocircleandroid.ui.theme.screens.components.Product

import NavItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NotFoundDeviceScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 안내 텍스트
        Text(
            text = "찾으시는 모델이 없습니다.",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "선택 입력 페이지로 이동할까요?",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 버튼들 사이에 충분한 여백을 주기 위해 Row 대신 Column을 사용
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // 버튼 사이 여백 추가
        ) {
            // '네' 버튼
            Button(
                onClick = { navController.navigate("home_collection") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp) // 버튼 양옆 여백 추가
            ) {
                Text("네")
            }

            // '아니오' 버튼
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp) // 버튼 양옆 여백 추가
            ) {
                Text("아니오")
            }
        }
    }
}
