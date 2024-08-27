package com.example.echocircleandroid.ui.theme.screens.components.Product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.Product
import com.example.echocircleandroid.ui.theme.screens.data.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun CheckDeviceScreen(
    navController: NavController,
    serialNumber: String // productId 대신 serialNumber 사용
) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Derived states for product details
    val id by remember { derivedStateOf { product?.id?.toString() ?: "" } }
    val name by remember { derivedStateOf { product?.name ?: "" } }
    val category by remember { derivedStateOf { product?.category ?: "" } }
    val company by remember { derivedStateOf { product?.company ?: "" } }
    val size by remember { derivedStateOf { product?.size?.toString() ?: "" } }
    val year by remember { derivedStateOf { product?.year?.toString() ?: "" } }
    val model by remember { derivedStateOf { product?.model ?: "" } }
    val serial by remember { derivedStateOf { product?.serial ?: "" } }
    val image by remember { derivedStateOf { product?.image ?: "" } }

    // Fetch product details on composition
    LaunchedEffect(serialNumber) {
        coroutineScope.launch {
            isLoading = true
            val fetchedProduct = fetchProductDetails(serialNumber)
            isLoading = false
            product = fetchedProduct
            if (fetchedProduct == null) {
                errorMessage = "제품 정보를 불러오는데 실패했습니다."
            }
        }
    }

    // Display UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            product?.let { prod ->
                ProductDetailView(product = prod)

                // 질문 및 버튼 추가
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "이 제품이 맞으신가요?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        // image빼고 모든 product정보 TotalCollectScreen을 넘기기, 이미지는 URL이라 특수문자들어가서 에러난다
                        onClick = { navController.navigate("total_collect_screen/$id/$name/$category/$company/$size/$year/$model/$serial") },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("예")
                    }
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("아니오")
                    }
                }
            } ?: run {
                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
                }
            }
        }
    }
}


@Composable
fun ProductDetailView(product: Product) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        product.image.let { imageUrl ->
            val painter: Painter = rememberImagePainter(imageUrl)
            Image(
                painter = painter,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = "제품 이름: ${product.name}",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "카테고리: ${product.category}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "브랜드: ${product.company}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "모델: ${product.model}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "연도: ${product.year}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

suspend fun fetchProductDetails(serialNumber: String): Product? {
    return try {
        val response: ProductResponse = withContext(Dispatchers.IO) {
            RetrofitInstance.api.getProduct(serialNumber) // serialNumber를 사용하여 API 호출
        }
        Log.d("fetchProductDetails", "Fetched ProductResponse: $response")
        if (response.httpStatus == "ACCEPTED") {
            Log.d("fetchProductDetails", "Fetched Product: ${response.product}")
            response.product
        } else {
            Log.d("fetchProductDetails", "HTTP Status not accepted: ${response.httpStatus}")
            null
        }
    } catch (e: Exception) {
        Log.e("fetchProductDetails", "Error fetching product info", e)
        null
    }
}
