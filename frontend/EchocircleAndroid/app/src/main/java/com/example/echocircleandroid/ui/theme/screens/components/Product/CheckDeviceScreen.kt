package com.example.echocircleandroid.ui.theme.screens.components.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.echocircleandroid.ui.theme.screens.data.Product

@Composable
fun CheckDeviceScreen(navController: NavController, product: Product) {
    var isCollectable by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Product 이미지
        Image(
            painter = rememberImagePainter(product.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.name, fontSize = 20.sp)
        Text(text = "Company: ${product.company}", fontSize = 16.sp)
        Text(text = "Year: ${product.year}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Button(onClick = {
                isCollectable = true // 적절한 로직으로 대체

                if (isCollectable) {
                    navController.navigate(NavItem.FoundDeviceScreen.screen_route) {
                        popUpTo(NavItem.CheckDeviceScreen.screen_route) { inclusive = true }
                    }
                } else {
                    navController.navigate(NavItem.CannotCollectDeviceScreen.screen_route) {
                        popUpTo(NavItem.CheckDeviceScreen.screen_route) { inclusive = true }
                    }
                }
            }) {
                Text(text = "네")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                navController.popBackStack() // 현재 화면을 스택에서 제거
            }) {
                Text(text = "아니오")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckDeviceScreenPreview() {
    CheckDeviceScreen(
        navController = rememberNavController(),
        product = Product(
            id = 1,
            name = "LG디오스 오브제 컬렉션",
            category = "냉장고",
            company = "LG",
            size = 3,
            year = 2022,
            model = "SQ07EJ3WES",
            serial = "0391383",
            image = "https://s3luna.s3.ap-northeast-2.amazonaws.com/echocircle/product/p%EB%83%89%EC%9E%A5%EA%B3%A01.jpg"
        )
    )
}
