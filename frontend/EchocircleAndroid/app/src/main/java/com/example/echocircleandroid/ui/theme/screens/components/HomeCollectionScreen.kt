import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCollectionScreen(navController: NavHostController) {
    var selectedBrand by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var expandBrand by remember { mutableStateOf(false) }
    var expandCategory by remember { mutableStateOf(false) }
    var expandSize by remember { mutableStateOf(false) }
    var expandYear by remember { mutableStateOf(false) }

    // Get the context
    val context = LocalContext.current

    val brands = listOf("삼성", "LG", "다이슨")
    val categories = listOf("냉장고", "에어컨", "세탁기")
    val sizes = listOf("소형", "중형", "대형")
    val years = (2024 downTo 2000).map { it.toString() }

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

        // 브랜드 선택
        ExposedDropdownMenuBox(
            expanded = expandBrand,
            onExpandedChange = { expandBrand = !expandBrand }
        ) {
            OutlinedTextField(
                value = selectedBrand,
                onValueChange = {},
                readOnly = true,
                label = { Text("브랜드") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandBrand)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expandBrand = !expandBrand }
            )
            ExposedDropdownMenu(
                expanded = expandBrand,
                onDismissRequest = { expandBrand = false }
            ) {
                brands.forEach { brand ->
                    DropdownMenuItem(
                        text = { Text(brand) },
                        onClick = {
                            selectedBrand = brand
                            expandBrand = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 카테고리 선택
        ExposedDropdownMenuBox(
            expanded = expandCategory,
            onExpandedChange = { expandCategory = !expandCategory }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("카테고리") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandCategory)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expandCategory = !expandCategory }
            )
            ExposedDropdownMenu(
                expanded = expandCategory,
                onDismissRequest = { expandCategory = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expandCategory = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 크기 선택
        ExposedDropdownMenuBox(
            expanded = expandSize,
            onExpandedChange = { expandSize = !expandSize }
        ) {
            OutlinedTextField(
                value = selectedSize,
                onValueChange = {},
                readOnly = true,
                label = { Text("크기") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandSize)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expandSize = !expandSize }
            )
            ExposedDropdownMenu(
                expanded = expandSize,
                onDismissRequest = { expandSize = false }
            ) {
                sizes.forEach { size ->
                    DropdownMenuItem(
                        text = { Text(size) },
                        onClick = {
                            selectedSize = size
                            expandSize = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 구매 년도 선택
        ExposedDropdownMenuBox(
            expanded = expandYear,
            onExpandedChange = { expandYear = !expandYear }
        ) {
            OutlinedTextField(
                value = selectedYear,
                onValueChange = {},
                readOnly = true,
                label = { Text("구매 연도") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandYear)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expandYear = !expandYear }
            )
            ExposedDropdownMenu(
                expanded = expandYear,
                onDismissRequest = { expandYear = false }
            ) {
                years.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year) },
                        onClick = {
                            selectedYear = year
                            expandYear = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val selectedSizeValue = mapSizeToValue(selectedSize)
                val selectedYearValue = selectedYear.toInt()
                // Add your navigation logic here
//                navController.navigate("total_collect_screen/$selectedBrand/$selectedCategory/$selectedSizeValue/$selectedYearValue")
                navController.navigate("total_collect_screen/0/none/$selectedCategory/$selectedBrand/$selectedSizeValue/$selectedYearValue/none/none/none")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("조회")
        }
    }
}

/* ToDo : 서버에 디바이스 정보가 있는지 없는지 여부 요청 */
private fun checkDeviceInfo(): Boolean {
    return false
}

private fun mapSizeToValue(size: String): Int {
    return when (size) {
        "소형" -> 1
        "중형" -> 2
        "대형" -> 3
        else -> 0
    }
}