import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
@Preview(showBackground = true)
fun DirectProcessingPreview(){
    UserGuideScreen(rememberNavController())
}
@Composable
fun UserGuideScreen(navController: NavController){
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
                text = "수거하실 폐가전의 바코드 번호를 찍어주세요.",
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
            Button(
                onClick = { navController.navigate("start_camera") },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "촬영하기")
            }
            Text(
                text = "바코드가 없으신가요?",
                style = TextStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 10.sp
                ),

                )
        }
    }
}