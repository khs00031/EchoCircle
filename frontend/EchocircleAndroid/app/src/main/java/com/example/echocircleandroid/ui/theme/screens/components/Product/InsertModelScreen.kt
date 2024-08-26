package com.example.echocircleandroid.ui.theme.screens.components.Product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import com.example.echocircleandroid.ui.theme.screens.data.SharedPreferencesUtil

@Preview(showBackground = true)
@Composable
fun FindModelScreenPreview() {
    // Preview content
}

// 모델명 기반으로 제품 검색
@Composable
fun InsertModelScreen(navController: NavHostController) {
    var selectedBrand by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    var expandBrand by remember { mutableStateOf(false) }
    var foundSerialNumber by remember { mutableStateOf(false) }

    // Get the context
    val context = LocalContext.current

    // Retrieve email and token from SharedPreferences
    val email = SharedPreferencesUtil.getUserEmail(context)
    val authToken = SharedPreferencesUtil.getAuthToken(context)

    val brands = listOf("삼성", "LG", "다이슨")

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
                brands.forEach { brand ->
                    DropdownMenuItem(text = { Text(brand) }, onClick = { selectedBrand = brand; expandBrand = false })
                }
            }

            TextField(
                value = serialNumber,
                onValueChange = { serialNumber = it },
                label = { Text("시리얼 넘버") },
                modifier = Modifier.padding(top = 50.dp)
            )

            Button(
                onClick = {
                    /* 서버로 요청 보내서 디바이스 정보 있는지 없는지 확인 */
                    foundSerialNumber = checkDeviceInfo()

                    if (foundSerialNumber) {
                        navController.navigate(NavItem.CheckDeviceScreen.screen_route)
                    } else {
                        navController.navigate(NavItem.NotFoundDeviceScreen.screen_route)
                    }
                },
                modifier = Modifier.padding(top = 110.dp)
            ) {
                Text("확인")
            }
        }

        // 다른페이지에서 email, token정보 불러오기 Test(SharedPreferencesUtil)
        email?.let {
            Text(text = "Email: $it", modifier = Modifier.padding(top = 16.dp))
        }

        authToken?.let {
            Text(text = "Token: $it", modifier = Modifier.padding(top = 8.dp))
        }
        // token, email불러오기 끝, 나중에 개발할때 지울것.
    }
}

/* ToDo : 서버에 디바이스 정보가 있는지 없는지 여부 요청 */
private fun checkDeviceInfo(): Boolean {
    return false
}
