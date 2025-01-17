package com.example.echocircleandroid.ui.theme.screens.components.Community

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.echocircleandroid.ui.theme.RetrofitInstance
import com.example.echocircleandroid.ui.theme.screens.data.Article
import com.example.echocircleandroid.ui.theme.screens.data.GetArticleResponse
import com.example.echocircleandroid.ui.theme.screens.data.GetArticlesResponse
import com.example.echocircleandroid.ui.theme.screens.data.RegistArticleResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CommunityViewModel(application: Application, private val articleId: Int?): AndroidViewModel(application) {

    var isLoading by mutableStateOf(false)
    val categories = listOf("냉장고", "세탁기|건조기", "에어컨", "선풍기", "청소기", "오븐", "공기청정기|제습기", "에어드레서", "인덕션|전자레인지", "식기세척기", "기타")

//    private val _aId = mutableStateOf<Int>(0)
//    var aId: MutableState<Int> = _aId

    private val _articleList = mutableStateOf(listOf<Article>())
    val articleList: State<List<Article>> = _articleList

    private val _article = mutableStateOf<Article?>(null)
    val article: State<Article?> = _article

    private val _errorMessage = mutableStateOf<String>("")
    val errorMessage: State<String> = _errorMessage

    private val _getArticlesResponse = MutableStateFlow<GetArticlesResponse?>(null)
    val getArticlesResponse: StateFlow<GetArticlesResponse?> = _getArticlesResponse

    private val _getArticleResponse = MutableStateFlow<GetArticleResponse?>(null)
    val getArticleResponse: StateFlow<GetArticleResponse?> = _getArticleResponse

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _selectedCategory = mutableStateOf("")
    val selectedCategory: State<String> = _selectedCategory

    private val _content = mutableStateOf("")
    val content: State<String> = _content

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: MutableState<Uri?> = _imageUri

    private val _communityResponse = MutableStateFlow<RegistArticleResponse?>(null)
    val communityResponse: StateFlow<RegistArticleResponse?> = _communityResponse

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    var navigationAction: ((String) -> Unit)? = null
    private val context: Context get() = getApplication<Application>().applicationContext

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun setSelectedCategory(newCategory: String) {
        _selectedCategory.value = newCategory
    }

    fun getCategoryName(categoryNumber: Int): String {
        if (categoryNumber in categories.indices) {
            return categories[categoryNumber]
        }
        return ""
    }

    fun setContent(newContent: String) {
        _content.value = newContent
    }

    fun setImageUri(newImageUri: String) {
        _imageUri.value = Uri.parse(newImageUri)
    }

    init {
        fetchArticles()
        articleId?.let {
            fetchArticleDetails(articleId)
        }
    }

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getArticles()
                _getArticlesResponse.value = response

                if (response.articles.isNotEmpty()) {
                    _articleList.value = response.articles
                }
                isLoading = true
            } catch (e: HttpException) {
                // 서버에서 반환된 에러 메시지를 읽어오기
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                _getArticlesResponse.value = GetArticlesResponse(
                    httpStatus = "UNAUTHORIZED",
                    message = errorMessage,
                    articles = listOf<Article>()
                )
            } catch (e: IOException) {
                _getArticlesResponse.value = GetArticlesResponse(
                    httpStatus = "INTERNAL_SERVER_ERROR",
                    message = "서버 내부 오류",
                    articles = listOf<Article>()
                )
            }
        }
    }

    fun fetchArticleDetails(aId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getArticle(articleId = aId)
                _getArticleResponse.value = response

                response.article?.let {
                    _getArticleResponse.value = response
                    setTitle(response.article.title)
                    setSelectedCategory(it.category)
                    setContent(response.article.content)
                    // 이미지 URI를 설정, 이미지가 존재하는 경우
                    if (response.article.images.isNotEmpty()) {
                        setImageUri(response.article.images[0])
                    }
                }

                isLoading = true
            } catch (e: HttpException) {
                // 서버에서 반환된 에러 메시지를 읽어오기
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                _getArticleResponse.value = GetArticleResponse(
                    httpStatus = "UNAUTHORIZED",
                    message = errorMessage,
                    article = null
                )
            } catch (e: IOException) {
                _getArticleResponse.value = GetArticleResponse(
                    httpStatus = "INTERNAL_SERVER_ERROR",
                    message = "서버 내부 오류",
                    article = null
                )
            }
        }
    }

//    fun onRegisterButtonClick() {
//        viewModelScope.launch {
////            val requestWriteArticleDto = RequestWriteArticleDto(
////                email = "aaa@aaa",
////                category = _categoryNumber.value,
////                title = _title.value,
////                content = _content.value,
////                shared = false
////            )
////            val registArticleRequest = RegistArticleRequest(
////                requestWriteArticleDto = requestWriteArticleDto,
////                thumbnail = _imageUri.value,
////                images = _imageUri.value
////            )
//
//            try {
//                // JSON 데이터를 RequestBody로 변환
//                val registWriteArticleDto = RequestWriteArticleDto(
//                    email = "aaa@aaa",
//                    category = _categoryNumber.value,
//                    title = _title.value,
//                    content = _content.value,
//                    shared = false
//                )
//
//                // JSON 변환
//                val gson = Gson()
//                val json = gson.toJson(registWriteArticleDto)
//
//                // RequestBody로 변환
//                val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//
////                val email = "aaa@aaa".toRequestBody("text/plain".toMediaTypeOrNull())
////                val category = _categoryNumber.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
////                val title = _title.value.toRequestBody("text/plain".toMediaTypeOrNull())
////                val content = _content.value.toRequestBody("text/plain".toMediaTypeOrNull())
////                val shared = "false".toRequestBody("text/plain".toMediaTypeOrNull())
//
//                // 파일을 MultipartBody.Part로 변환
////                val thumbnailPart: MultipartBody.Part? = _imageUri.value?.let { uri ->
////                    val file = File(uri.path ?: "")
////                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
////                    MultipartBody.Part.createFormData("thumbnail", file.name, requestFile)
////                }
//                val thumbnailPart = _imageUri.value?.let { uri ->
//                    val file = File(uri.path)
//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    MultipartBody.Part.createFormData("thumbnail", file.name, requestFile)
//                }
//
//                // 이미지 리스트를 MultipartBody.Part로 변환 (단일 이미지인 경우에도 리스트로 만들어서 처리)
//                val imagesParts: List<MultipartBody.Part>? = _imageUri.value?.let { uri ->
//                    val file = File(uri.path ?: "")
//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    listOf(MultipartBody.Part.createFormData("images", file.name, requestFile))
//                }
////                val imagesParts = _imageUri.value?.let { uri ->
////                    val file = File(uri.path)
////                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
////                    MultipartBody.Part.createFormData("images", file.name, requestFile)
////                }
//
//                // Retrofit API 호출
////                val response: RegistArticleResponse = RetrofitInstance.api.registArticle(
////                    email = email,
////                    category = category,
////                    title = title,
////                    content = content,
////                    shared = shared,
////                    thumbnail = thumbnailPart,
////                    images = imagesParts
////                )
//                val response: RegistArticleResponse = RetrofitInstance.api.registArticle(
//                    request = requestBody,
//                    thumbnail = thumbnailPart,
//                    images = imagesParts
//                )
//
////                val response = RetrofitInstance.api.registArticle(registArticleRequest)
//                _communityResponse.value = response
//                Log.d("ing..............", "ing")
//                setShowDialog(true)
//
//                if (response.httpStatus.toInt() >= 200) {
//                    Log.d("result::::::::::::::::::", "200!!!!!!")
//                    // 안내 메시지 창 띄우기
//                    setShowDialog(true)  // 다이얼로그 표시
//                }
//            } catch (e: HttpException) {
//                // 서버에서 반환된 에러 메시지를 읽어오기
//                val errorBody = e.response()?.errorBody()?.string()
//                val errorMessage = parseErrorMessage(errorBody)
//                _communityResponse.value = RegistArticleResponse(
//                    httpStatus = "UNAUTHORIZED",
//                    message = errorMessage
//                )
//                Log.d("Error!!!!!!", errorMessage)
//            } catch (e: IOException) {
//                _communityResponse.value = RegistArticleResponse(
//                    httpStatus = "INTERNAL_SERVER_ERROR",
//                    message = "서버 내부 오류"
//                )
//                Log.d("Error!!!!!!", "server error!!!!1")
//            }
////            Log.d("category!!!!!!!!!!", _categoryNumber.value.toString())
////            Log.d("title!!!!!!!!!!!!", _title.value)
////            Log.d("content!!!!!!!!!!", _content.value)
////            val success = withContext(Dispatchers.IO) {
////                submitPost(postData)
////            }
////            if (success) {
////                navigationAction?.invoke("successPage") // Navigate on success
////            } else {
////                // Handle failure (e.g., show a message)
////            }
//        }
//    }

    // Helper function to parse error message from JSON
    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
            jsonObject.get("message")?.asString ?: "알 수 없는 오류"
        } catch (e: Exception) {
            "알 수 없는 오류"
        }
    }
}