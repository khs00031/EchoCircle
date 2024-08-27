package com.example.echocircleandroid.ui.theme.screens

import BottomNavItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.echocircleandroid.R

@Composable
fun LoginScreen(navController : NavHostController){

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sub_logo),
                contentDescription = "서브 로고",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(horizontal = 30.dp)
            )
//            Text(
//                text = stringResource(id = R.string.app_name),
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Bold,
//                color = colorResource(id = R.color.black) // 보라색 텍스트
//            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("member_login_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
//                colors = ButtonDefaults.buttonColors(Color(0xFF3B5998)) // 카카오 색상
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
            ) {
                Text(text = "회원 로그인", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(BottomNavItem.ProductCollect.screen_route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green))
            ) {
                Text(text = "비회원 로그인", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("member_regist_screen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(text = "회원가입", color = Color.Black)
            }
        }
    }

}
