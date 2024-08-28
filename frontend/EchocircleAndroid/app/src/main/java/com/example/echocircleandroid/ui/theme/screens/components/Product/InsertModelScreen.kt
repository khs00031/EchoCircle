package com.example.echocircleandroid.ui.theme.screens.components.Product

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.echocircleandroid.ui.theme.screens.data.Product
import kotlinx.coroutines.launch

// 제품 모델명 or 시리얼을 직접 입력하는 화면
@Composable
fun InsertModelScreen(navController: NavController, serialNumber: String = "") {
    var selectedBrand by remember { mutableStateOf("") }
    var currentSerialNumber by remember { mutableStateOf(serialNumber) }
    var expandBrand by remember { mutableStateOf(false) }
    var foundProduct by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) } // 에러 메시지 표시 여부

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "수거하실 폐가전의 정보를 입력해주세요.",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        // 여백 추가하여 에러 메시지 공간 확보
        Spacer(modifier = Modifier.height(20.dp)) // 고정된 높이의 빈 공간 추가

        if (showError) {
            Text(
                text = "모델명 or 시리얼넘버를 입력하세요.",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(15.dp)) // 에러 메시지가 없을 때의 여백 확보
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = currentSerialNumber,
                onValueChange = { currentSerialNumber = it },
                label = { Text("시리얼 넘버") },
//                modifier = Modifier.padding(top = 5.dp)
            )

            Button(
                onClick = {
                    if (currentSerialNumber.isEmpty()) {
                        showError = true
                    } else {
                        showError = false
                        coroutineScope.launch {
                            isLoading = true
                            val product = fetchProductDetails(currentSerialNumber) // serialNumber로 조회
                            isLoading = false
                            if (product != null) {
                                navController.navigate("check_device_screen/${currentSerialNumber}") // 제품 ID를 이용해 내비게이션
                            } else {
                                navController.navigate(NavItem.NotFoundDeviceScreen.screen_route)
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 15.dp),

            ) {
                Text("확인")
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            }
        }
    }
}
