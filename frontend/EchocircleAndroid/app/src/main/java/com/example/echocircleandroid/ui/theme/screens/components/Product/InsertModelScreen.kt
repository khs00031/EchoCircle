// 파일: InsertModelScreen.kt
package com.example.echocircleandroid.ui.theme.screens.components.Product

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.Product
import com.example.echocircleandroid.ui.theme.screens.data.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@Composable
fun InsertModelScreen(navController: NavController, serialNumber: String = "") {
    var selectedBrand by remember { mutableStateOf("") }
    var currentSerialNumber by remember { mutableStateOf(serialNumber) }
    var expandBrand by remember { mutableStateOf(false) }
    var foundProduct by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(false) }

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
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box {
            Button(onClick = { expandBrand = !expandBrand }) {
                Text(text = if (selectedBrand.isEmpty()) "브랜드 선택" else selectedBrand)
            }
            DropdownMenu(expanded = expandBrand, onDismissRequest = { expandBrand = false }) {
                val brands = listOf("삼성", "LG", "다이슨")
                brands.forEach { brand ->
                    DropdownMenuItem(
                        text = { Text(brand) },
                        onClick = {
                            selectedBrand = brand
                            expandBrand = false
                        }
                    )
                }
            }

            TextField(
                value = currentSerialNumber,
                onValueChange = { currentSerialNumber = it },
                label = { Text("시리얼 넘버") },
                modifier = Modifier.padding(top = 50.dp)
            )

            Button(
                onClick = {
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
                },
                modifier = Modifier.padding(top = 110.dp)
            ) {
                Text("확인")
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            }
        }
    }
}

