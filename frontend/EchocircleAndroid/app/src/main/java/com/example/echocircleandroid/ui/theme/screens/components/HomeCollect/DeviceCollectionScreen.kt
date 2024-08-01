import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Preview(showBackground = true)
@Composable
fun HomeCollectionScreenPreview() {

}

@Composable
fun HomeCollectionScreen(navController: NavHostController) {

    var selectedBrand by remember{ mutableStateOf("") }
    var serialNumber by remember{ mutableStateOf("") }
    var expandBrand by remember{ mutableStateOf(false) }
    var foundSerialNumber by remember{ mutableStateOf(false) }

    val brands = listOf("삼성","LG","다이슨")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){

        Text(
            text = "수거하실 폐가전의 정보를 입력해주세요.",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Box{
            Button(onClick = { expandBrand = !expandBrand }) {
                Text(text = if (selectedBrand.isEmpty()) "브랜드 선택" else selectedBrand)
            }
            DropdownMenu(expanded = expandBrand, onDismissRequest = {expandBrand = false}) {
                brands.forEach { brand ->
                    DropdownMenuItem(text = { Text(brand) }, onClick = { selectedBrand = brand; expandBrand = false })
                }
            }

            TextField(
                value = serialNumber,
                onValueChange = {serialNumber = it },
                label = { Text("시리얼 넘버")},
                modifier = Modifier.padding(top = 50.dp)
                )

            Button(
                onClick = {
                    /* 서버로 요청 보내서 디바이스 정보 있는지 없는지 확인 */
                    foundSerialNumber = checkDeviceInfo()

                    if(foundSerialNumber){
                        navController.navigate(NavItem.CheckDeviceScreen.screen_route)
                    }else{
                        navController.navigate(NavItem.NotFoundDeviceScreen.screen_route)
                    }



                },
                modifier = Modifier.padding(top = 110.dp),
            ) {
                Text("확인")
            }
        }


    }
}

/*ToDo : 서버에 디바이스 정보가 있는지 없는지 여부 요청*/
private fun checkDeviceInfo() : Boolean{
    return false
}
