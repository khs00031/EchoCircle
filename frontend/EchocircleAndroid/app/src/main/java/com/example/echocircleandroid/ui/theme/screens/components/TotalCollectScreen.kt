package com.example.echocircleandroid.ui.theme.screens.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.echocircleandroid.ui.theme.screens.components.Product.CollectViewModel
import com.example.echocircleandroid.ui.theme.screens.data.Product
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


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
            .width(30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

//        Spacer(modifier = Modifier.height(8.dp))
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .background(color = Color.Gray),
//            contentAlignment = Alignment.Center
//        ) {
////            product.image.let { imageUrl ->
//                Image(
//                    painter = rememberAsyncImagePainter(product.image),
//                    contentDescription = "Selected Image",
//                    modifier = Modifier.size(100.dp)
//                )
////            }
//        }

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
                value = mapValueToSize(product.size),
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
                text = "시리얼\n번호: ",
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
        },
        product = product
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
                value = mapValueToSize(product.size),
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
        },
        product = product
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

@OptIn(ExperimentalPagerApi::class)
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
                    // Information pager
                    val pagerState = rememberPagerState()

                    HorizontalPager(
                        count = 1,
                        state = pagerState,
                        modifier = Modifier.height(300.dp)
                    ) { page ->
                        when (page) {
                            0 -> ProcessingPage1()
                        }
                    }
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProcessingCompanyDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    product: Product
) {
    var dialogInput by remember { mutableStateOf("") }
    val context = LocalContext.current

//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { onDismissRequest() },
//            title = { Text(text = "회사 수거 안내") },
//            text = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp)
//                        .height(300.dp),
//                ) {
//                    Text(
//                        text = when (product.company) {
//                            "LG" -> {
//                                buildAnnotatedString {
//                                    withStyle(style = SpanStyle(color = Color.Blue)) {
//                                        append("LG전자 서비스센터: 1588-7777")
//                                    }
//                                }
//                            }
//                            "삼성" -> {
//                                buildAnnotatedString {
//                                    withStyle(style = SpanStyle(color = Color.Blue)) {
//                                        append("삼성전자 서비스센터: 1588-3366")
//                                    }
//                                }
//                            }
//                            else -> {
//                                buildAnnotatedString {
//                                    withStyle(style = SpanStyle(color = Color.Blue)) {
//                                        append("해당 브랜드의 서비스 센터에 연락하여 수거 예약을 진행해 주세요.")
//                                    }
//                                }
//                            }
//                        },
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.clickable {
//                            when (product.company) {
//                                "LG" -> {
//                                    val phoneNumber = "1588-7777"
//                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
//                                    context.startActivity(intent)
//                                }
//                                "삼성" -> {
//                                    val phoneNumber = "1588-3366"
//                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
//                                    context.startActivity(intent)
//                                }
//                                else -> {
//                                    // For other brands, you may want to handle it differently
//                                }
//                            }
//                        }
//                    )
//                }
//            },
//            confirmButton = {
//                Button(onClick = {
//                    onConfirm(dialogInput)
//                }) {
//                    Text("확인")
//                }
//            }
//        )
//    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "방문 수거 안내") },
            text = {
                Column {
                    // Information pager
                    val pagerState = rememberPagerState()

                    HorizontalPager(
                        count = 1,
                        state = pagerState,
                        modifier = Modifier.height(300.dp)
                    ) { page ->
                        when (page) {
                            0 -> ProcessingPage5(product)
                        }
                    }
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

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
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
            title = { Text(text = "처리 방법 안내") },
            text = {
                Column {
                    // Information pager
                    val pagerState = rememberPagerState()

                    HorizontalPager(
                        count = 3,
                        state = pagerState,
                        modifier = Modifier.height(300.dp)
                    ) { page ->
                        when (page) {
                            0 -> ProcessingPage2()
                            1 -> ProcessingPage3()
                            2 -> ProcessingPage4()
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Page Indicator and Navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}",
                            fontSize = 14.sp
                        )
                    }
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
fun ProcessingPage1() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "폐가전 무상 수거 서비스",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 15.dp),
        )

        Text(
            text = "대형 폐가전은 1개 이상, 소형 폐가전은 5개 이상 배출 시 무상으로 수거해 줍니다. 전화나 온라인으로 예약 가능합니다. \n\n",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text(
                text = "전화: ",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )

            // Handle clickable phone number
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("1599-0903")
                    }
                },
                onClick = { offset ->
                    // Launch the dialer with the phone number
                    val phoneNumber = "1599-0903"
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    context.startActivity(intent)
                }
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        ) {
            Text(
                text = "온라인: ",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("폐가전제품 무상 방문수거 홈페이지 바로가기")
                    }
                },
                onClick = { offset ->
                    val link = "https://15990903.or.kr/portal/main/main.do"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun ProcessingPage2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "1. 동주민센터 방문 처리",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "동주민센터에 방문하여 소형 폐가전을 배출할 수 있습니다. 배출 후, 지정된 날짜에 수거해 갑니다. \n\n 배출 방법: 폐가전을 동주민센터에 직접 가져다 주시면 됩니다. 또는, 해당 구청의 환경과에 문의하면 자세한 배출 방법을 안내받을 수 있습니다.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProcessingPage3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "2. 재활용센터, 주민센터 수거함 이용",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "일부 재활용센터나 주민센터에 소형 폐가전 전용 수거함이 설치되어 있습니다. 이를 이용하여 소형 폐가전을 배출할 수 있습니다. \n\n" +
                    "예시: 배터리, 형광등, 작은 전자제품(휴대폰, 충전기 등)을 전용 수거함에 배출할 수 있습니다.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProcessingPage4() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "3. 대형마트 및 전자제품 판매점 반납 서비스",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "새 전자제품 구매 시, 기존 소형 폐가전을 무료로 반납할 수 있습니다. 대형마트나 전자제품 판매점에서 가능하며, 반납 조건을 확인하세요. \n",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProcessingPage5(product: Product) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .height(300.dp),
    ) {
        Text(
            text = when (product.company) {
                "LG" -> {
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("LG전자 서비스센터: 1588-7777")
                        }
                    }
                }
                "삼성" -> {
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("삼성전자 서비스센터: 1588-3366")
                        }
                    }
                }
                else -> {
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("해당 브랜드의 서비스 센터에 연락하여 수거 예약을 진행해 주세요.")
                        }
                    }
                }
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
                when (product.company) {
                    "LG" -> {
                        val phoneNumber = "1588-7777"
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        context.startActivity(intent)
                    }
                    "삼성" -> {
                        val phoneNumber = "1588-3366"
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        context.startActivity(intent)
                    }
                    else -> {
                        // For other brands, you may want to handle it differently
                    }
                }
            }
        )
    }
}

private fun mapValueToSize(size: Int): String {
    return when (size) {
        1 -> "소형"
        2 -> "중형"
        3 -> "대형"
        else -> ""
    }
}