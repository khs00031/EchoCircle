package com.example.echocircleandroid.ui.theme.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.echocircleandroid.ui.theme.screens.components.Product.CollectViewModel
import com.example.echocircleandroid.ui.theme.screens.data.Product


@Composable
fun TotalCollectScreen(navController: NavHostController, product: Product, collectViewModel: CollectViewModel) {

    // Get the context
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "가전 정보 확인", fontSize = 22.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp))

        Spacer(modifier = Modifier.height(20.dp))

        if (product.id == 0) {
            DisplayProductSubInfo(product, collectViewModel)
        } else {
            DisplayProductInfo(product, collectViewModel)
        }
    }

}

/* ToDo : 서버에 디바이스 정보가 있는지 없는지 여부 요청 */
private fun checkDeviceInfo(): Boolean {
    return false
}

@Composable
fun DisplayProductInfo(product: Product, collectViewModel: CollectViewModel) {

    val isLoading by collectViewModel.isLoading.collectAsState()
    val visitCollect by collectViewModel.visitCollect.collectAsState()
    val companyCollect by collectViewModel.companyCollect.collectAsState()

    var showVisitDialog by remember { mutableStateOf(false) }
    var showCompanyDialog by remember { mutableStateOf(false) }
    var showDirectDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Image selection section
        Box(
            modifier = Modifier
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            product.image?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "제품명: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.name,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "분류: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.category,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "브랜드: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.company,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "크기: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.size.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "구매 연도: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.year.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "모델명: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.model,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "시리얼 번호: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.serial,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                collectViewModel.fetchProduct(product)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("수거 방법 조회")
        }


        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "조회 결과", fontSize = 16.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally))

            Button(
                onClick = { showVisitDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = visitCollect
            ) {
                Text("방문 수거 가능")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showCompanyDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = companyCollect
            ) {
                Text("회사 수거 가능")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDirectDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("직접 처리")
            }
        }
    }

    // Display the dialog if showVisitDialog is true
    ProcessingVisitDialog(
        showDialog = showVisitDialog,
        onDismissRequest = { showVisitDialog = false },
        onConfirm = { input ->
            showVisitDialog = false
        }
    )

    // Display the dialog if showCompanyDialog is true
    ProcessingCompanyDialog(
        showDialog = showCompanyDialog,
        onDismissRequest = { showCompanyDialog = false },
        onConfirm = { input ->
            showCompanyDialog = false
        }
    )

    // Display the dialog if showDialog is true
    ProcessingDirectDialog(
        showDialog = showDirectDialog,
        onDismissRequest = { showDirectDialog = false },
        onConfirm = { input ->
            showDirectDialog = false
        }
    )
}

@Composable
fun DisplayProductSubInfo(product: Product, collectViewModel: CollectViewModel) {

    val isLoading by collectViewModel.isLoading.collectAsState()
    val visitCollect by collectViewModel.visitCollect.collectAsState()
    val companyCollect by collectViewModel.companyCollect.collectAsState()

    var showVisitDialog by remember { mutableStateOf(false) }
    var showCompanyDialog by remember { mutableStateOf(false) }
    var showDirectDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .width(70.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "분류: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.category,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "브랜드: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.company,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "크기: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.size.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "구매 연도: ",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(70.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = product.year.toString(),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                collectViewModel.fetchProductCollectableSub(product)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("수거 방법 조회")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "조회 결과", fontSize = 16.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally))

            Button(
                onClick = { showVisitDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = visitCollect
            ) {
                Text("방문 수거 가능")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showCompanyDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = companyCollect
            ) {
                Text("회사 수거 가능")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDirectDialog = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text("직접 처리")
            }
        }
    }

    // Display the dialog if showVisitDialog is true
    ProcessingVisitDialog(
        showDialog = showVisitDialog,
        onDismissRequest = { showVisitDialog = false },
        onConfirm = { input ->
            showVisitDialog = false
        }
    )

    // Display the dialog if showCompanyDialog is true
    ProcessingCompanyDialog(
        showDialog = showCompanyDialog,
        onDismissRequest = { showCompanyDialog = false },
        onConfirm = { input ->
            showCompanyDialog = false
        }
    )

    // Display the dialog if showDialog is true
    ProcessingDirectDialog(
        showDialog = showDirectDialog,
        onDismissRequest = { showDirectDialog = false },
        onConfirm = { input ->
            showDirectDialog = false
        }
    )
}

@Composable
fun ProcessingVisitDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var dialogInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "방문 수거 안내") },
            text = {
                Column {
                    OutlinedTextField(
                        value = dialogInput,
                        onValueChange = { dialogInput = it },
                        label = { Text("처리 방법") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(dialogInput)
                }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun ProcessingCompanyDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var dialogInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "회사 수거 안내") },
            text = {
                Column {
                    OutlinedTextField(
                        value = dialogInput,
                        onValueChange = { dialogInput = it },
                        label = { Text("처리 방법") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(dialogInput)
                }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun ProcessingDirectDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var dialogInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "처리 방법 작성") },
            text = {
                Column {
                    OutlinedTextField(
                        value = dialogInput,
                        onValueChange = { dialogInput = it },
                        label = { Text("처리 방법") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(dialogInput)
                }) {
                    Text("확인")
                }
            }
        )
    }
}
