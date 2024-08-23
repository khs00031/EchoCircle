package com.example.echocircleandroid.ui.theme.screens.components.Community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.echocircleandroid.ui.theme.EchocircleAndroidTheme
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Retrofit 설정 및 인터페이스 정의
interface ApiService {
    @GET("api/board")
    fun getArticles(): Call<ApiResponse>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

// 데이터 모델 정의
data class Article(
    val aid: Int,
    val nickname: String,
    val category: Int,
    val title: String,
    val content: String,
    val registTime: String,
    val thumbnail: String?,
    val shared: Boolean,
    val view: Int,
    val deleted: Boolean
)

data class ApiResponse(val articles: List<Article>)

// ViewModel 정의
class CommunityViewModel : ViewModel() {
    var articles by mutableStateOf(listOf<Article>())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchArticles() {
        isLoading = true
        RetrofitClient.apiService.getArticles().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    articles = response.body()?.articles ?: emptyList()
                } else {
                    errorMessage = "서버 오류: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                isLoading = false
                errorMessage = "통신 오류: ${t.message}"
            }
        })
    }
}

// Composable 함수들 정의
@Composable
fun CommunityMainScreen(navController: NavController) {
    val communityViewModel: CommunityViewModel = viewModel()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        communityViewModel.fetchArticles()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(searchText = searchText, onValueChange = { searchText = it }) {
            communityViewModel.fetchArticles()
        }
        Spacer(modifier = Modifier.height(8.dp))
        SharingCriteriaBar()

        if (communityViewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (communityViewModel.errorMessage != null) {
            Text(
                text = communityViewModel.errorMessage!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            communityViewModel.articles.forEach { article ->
                CommunityCard(
                    data = CommunityCardData(
                        thumbnailUrl = article.thumbnail ?: "",
                        title = article.title,
                        category = article.category.toString(),
                        date = article.registTime,
                        isCompleted = article.shared
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onValueChange: (String) -> Unit, onSearchClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = onValueChange,
            placeholder = { Text("검색어를 입력하세요") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onSearchClick,
            modifier = Modifier.height(50.dp)
        ) {
            Text("검색")
        }
    }
}

@Composable
fun SharingCriteriaBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f)
        ) {
            Text("정렬 기준")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.weight(1f)
        ) {
            Text("나눔글 등록")
        }
    }
}

@Composable
fun CommunityCard(data: CommunityCardData) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(data.thumbnailUrl),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text(data.title, fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                Text("카테고리: ${data.category}", fontSize = 13.sp, color = Color.Black)
                Text("등록일: ${data.date}", fontSize = 13.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = if (data.isCompleted) "나눔 완료" else "나눔 중",
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

// Dummy Data Class
data class CommunityCardData(
    val thumbnailUrl: String,
    val title: String,
    val category: String,
    val date: String,
    val isCompleted: Boolean
)

//// Preview Composables
//@Preview(showBackground = true)
//@Composable
//fun CommunityMainScreenPreview() {
//    EchocircleAndroidTheme {
//        // 실제 NavController가 없으므로 null로 전달합니다.
//        CommunityMainScreen(navController = NavController(null))
//    }
//}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    EchocircleAndroidTheme {
        SearchBar(searchText = "", onValueChange = {}, onSearchClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SharingCriteriaBarPreview() {
    EchocircleAndroidTheme {
        SharingCriteriaBar()
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityCardPreview() {
    EchocircleAndroidTheme {
        CommunityCard(
            data = CommunityCardData(
                thumbnailUrl = "https://s3luna.s3.ap-northeast-2.amazonaws.com/echocircle/product/redis.png",
                title = "가질 사람",
                category = "냉장고",
                date = "2024.08.08",
                isCompleted = false
            )
        )
    }
}
