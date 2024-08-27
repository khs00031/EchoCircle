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
fun HomeCollectionScreenPreview() {
    // Preview content
}

// 지은 해야할것.
// 모델명, 시리얼넘버를 입력못할때 모델이 없을때 처리 프로세스를 아래에 만들것.
// !!!! 시리얼넘버, 모델명등은 여기서 처리할 필요없음(ProductCollectScreen에서 처리함) !!!!!
// size, category, company등을 입력받고 그것을 기반으로 처리방법(기업수거,순환거버넌스방문수거,스스로직접버리기방법안내) 페이지로 이동만 시켜줄것.
// Postman에 Prodcut폴더의 "기업 및 방문수거 가능여부" API 구현되어 있음.
// 아래 코드는 가라라서 다 지우고 새로해도 됨.
@Composable
fun HomeCollectionScreen(navController: NavHostController) {
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
            Text(text = "Email: $it"+"aaa", modifier = Modifier.padding(top = 16.dp))
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
