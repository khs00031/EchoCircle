package com.example.echocircleandroid.ui.theme.screens.components.Member

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun MemberRegistScreen(navController: NavHostController) {
    val context = LocalContext.current // Context를 얻기 위한 코드
    val viewModel: MemberRegistViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val address by viewModel.address.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val registResponse by viewModel.registResponse.collectAsState()

    // 중복 체크 상태
    var isEmailChecked by remember { mutableStateOf(false) }
    var isNicknameChecked by remember { mutableStateOf(false) }

    // 메시지를 저장할 상태 변수
    var infoMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.green)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // 스크롤 가능하게 설정
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

            Spacer(modifier = Modifier.height(20.dp))

            // 안내 메시지
            infoMessage?.let {
                Text(text = it, color = if (it.contains("가능")) Color.White else Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = email,
                    onValueChange = {
                        viewModel.onEmailChange(it)
                        isEmailChecked = false // 이메일이 변경되면 중복 체크 상태 초기화
                    },
                    label = { Text("이메일") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val emailExists = viewModel.checkEmailDuplicate(email)
                            isEmailChecked = !emailExists
                            infoMessage = if (!emailExists) "사용 가능한 이메일입니다." else "이미 사용 중인 이메일입니다."
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(text = "중복 확인")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = nickname,
                    onValueChange = {
                        viewModel.onNicknameChange(it)
                        isNicknameChecked = false // 닉네임이 변경되면 중복 체크 상태 초기화
                    },
                    label = { Text("닉네임") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val nicknameExists = viewModel.checkNicknameDuplicate(nickname)
                            isNicknameChecked = !nicknameExists
                            infoMessage = if (!nicknameExists) "사용 가능한 닉네임입니다." else "이미 사용 중인 닉네임입니다."
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(text = "중복 확인")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 주소와 전화번호는 선택 입력 필드
            TextField(
                value = address,
                onValueChange = { viewModel.onAddressChange(it) },
                label = { Text("주소 (선택 입력)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = phone,
                onValueChange = { viewModel.onPhoneChange(it) },
                label = { Text("전화번호 (선택 입력)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 회원가입 버튼은 이메일, 닉네임 중복 체크가 완료되면 활성화됨
            Button(
                onClick = {
                    infoMessage = null // 오류 메시지 초기화
                    coroutineScope.launch {
                        viewModel.registMember()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                enabled = isEmailChecked && isNicknameChecked, // 중복 체크가 완료되면 버튼 활성화
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.yellow))
            ) {
                Text(text = "회원가입", color = Color.Black)
            }

            // 회원가입 결과 처리
            registResponse?.let {
                Spacer(modifier = Modifier.height(16.dp))
                if (it.httpStatus == "ACCEPTED") {
                    Text(text = it.message, color = Color.White)
                    // 회원가입 성공 후 Toast 메시지 및 화면 이동
                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        // 회원가입 화면을 뒤로 스택에서 제거
                        popUpTo("member_regist_screen") { inclusive = true }
                    }
                } else {
                    // 회원가입 실패, 에러 메시지 출력
                    Text(text = it.message, color = Color.Red)
                }
            }
        }
    }
}
