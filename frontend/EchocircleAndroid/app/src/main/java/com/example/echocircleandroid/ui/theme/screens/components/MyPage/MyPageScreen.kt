import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echocircleandroid.R
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyPageViewModel
import com.example.echocircleandroid.ui.theme.screens.data.MypageRequest
import com.example.echocircleandroid.ui.theme.screens.data.SharedPreferencesUtil
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(navController: NavController, myPageViewModel: MyPageViewModel) {
    val nickname by myPageViewModel.nickname

    // logout test용 시작
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 설정
    // logout test용 끝, 나중에 지울것.

    // Retrieve email and token from SharedPreferences
    val email = SharedPreferencesUtil.getUserEmail(context)
    val token = SharedPreferencesUtil.getAuthToken(context)

    email?.let { e ->
        token?.let { t ->
            myPageViewModel.fetchNickname(e, t)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
//            .background(Color(0xFFE0F7FA)), // Background color similar to the image
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Top Section with Text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA)) // Light blue background color
                .padding(8.dp)
        ) {
            Text(
                text = "$nickname 님 덕에 지구온도가 낮아졌어요!",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Section Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "수거 처리 현황",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFB2EBF2), RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Row with buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Date Button
                Button(
                    onClick = { /* Handle date button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "2024.07.01")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Product Photo Button
                Button(
                    onClick = { /* Handle product photo button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "청소기")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Completed Button
                Button(
                    onClick = { /* Handle completed button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "접수 완료")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lower Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        navController.navigate("my_appliances")
                    }
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "수거한 가전 조회",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .clickable {
                        navController.navigate("my_written_posts")
                    }
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "작성한 나눔글 조회",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        // Logout Button 시작
        Button(
            onClick = {
                coroutineScope.launch {
                    val email = SharedPreferencesUtil.getUserEmail(context) ?: return@launch
                    val token = SharedPreferencesUtil.getAuthToken(context) ?: return@launch

                    try {
                        val response = RetrofitInstance.api.logout(MypageRequest(email, token))
                        if (response.httpStatus == "ACCEPTED") {
                            // 로그아웃 성공 처리
                            SharedPreferencesUtil.clearAll(context) // 모든 SharedPreferences 데이터 삭제

                            // 네비게이션 스택을 초기화하고 로그인 화면으로 이동
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true } // 모든 이전 화면을 제거
                                launchSingleTop = true // 동일한 화면이 여러 번 스택에 쌓이는 것을 방지
                            }
                        } else {
                            // 오류 메시지 처리
                        }
                    } catch (e: Exception) {
                        // 오류 처리
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "로그아웃",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        // logout button 끝, 나중에 지울것

    }
}