package com.example.echocircleandroid.ui.theme.screens.components.Product;

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.echocircleandroid.R

@Composable
fun ProdcutCollectScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "제품의 모델명 or 시리얼코드를 입력주세요.",
                modifier = Modifier
                    .padding(16.dp),
                fontSize = 16.sp
            )
            Image(
                painter = painterResource(id = R.drawable.deviceuserguide),
                contentDescription = "사용자 촬영 설명",
                modifier = Modifier
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("GetTextWithCameraScreen") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "촬영하기")
                }
                Button(
                    onClick = { navController.navigate("insert_model_screen") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "직접입력")
                }
            }

            Text(
                text = "모델명을 모르시나요?",
                style = TextStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("select_model_screen")
                    }
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun ProdcutCollectScreenPreview() {
    ProdcutCollectScreen(rememberNavController())
}