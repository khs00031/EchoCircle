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
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echocircleandroid.R
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyPageViewModel

@Composable
fun MyPageScreen(navController: NavController, myPageViewModel: MyPageViewModel) {
    val nickname by myPageViewModel.nickname

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

    }
}