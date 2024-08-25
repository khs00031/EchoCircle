package com.example.echocircleandroid.ui.theme.screens.components

import MemberLoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.echocircleandroid.R
import kotlinx.coroutines.launch


@Composable
fun MemberLoginScreen(navController: NavHostController) {
    val viewModel: MemberLoginViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginResponse by viewModel.loginResponse.collectAsState()

    // 메시지를 저장할 상태 변수
    var errorMessage by remember { mutableStateOf<String?>(null) }

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

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("이메일") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // 이메일과 비밀번호가 모두 입력되었는지 확인
                    if (email.isEmpty()) {
                        errorMessage = "Email을 입력하세요"
                    } else if (password.isEmpty()) {
                        errorMessage = "비밀번호를 입력하세요"
                    } else {
                        errorMessage = null // 오류 메시지 초기화
                        coroutineScope.launch {
                            viewModel.login()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
            ) {
                Text(text = "로그인", color = Color.Black)
            }

            // 오류 메시지가 존재하면 화면에 표시
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = Color.Red)
            }

            // 로그인 응답 메시지 표시
            loginResponse?.let {
                Spacer(modifier = Modifier.height(16.dp))
                if (it.httpStatus == "ACCEPTED") {
                    Text(text = it.message, color = Color.White)
                    // 로그인 성공 후 화면 이동
                    navController.navigate(BottomNavItem.DirectProcessing.screen_route)
                } else {
                    Text(text = it.message, color = Color.Red)
                }
            }
        }
    }
}
