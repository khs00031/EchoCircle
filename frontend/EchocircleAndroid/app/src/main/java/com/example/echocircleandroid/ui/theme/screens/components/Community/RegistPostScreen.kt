package com.example.echocircleandroid.ui.theme.screens.components.Community

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistPostScreen(
    navController: NavController,
    viewModel: CommunityViewModel = viewModel(),
    isEditMode: Boolean = false,
    articleId: Int? = null
) {
//    val viewModel: CommunityViewModel = viewModel()
    val article by viewModel.article

    val title by viewModel.title
    val selectedCategory by viewModel.selectedCategory
    val content by viewModel.content
    val imageUri by viewModel.imageUri
    val showDialog by viewModel.showDialog

//    var title by remember { mutableStateOf("") }
//    var categoryNumber by remember { mutableStateOf("") }
//    var selectedCategory by remember { mutableStateOf("") }
//    var content by remember { mutableStateOf("") }
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Handle API fetch in edit mode
//    if (!isEditMode && articleId != null) {
//        LaunchedEffect(articleId) {
//            viewModel.fetchArticleDetails(articleId) // Fetch article details
//
////            viewModel.setTitle(article.title)
//            if (article != null) {
//                viewModel.setContent(article!!.content)
//                Log.d("1234!!!!!!!!!!!", article!!.content)
//            }
//        }
//    }

    // Trigger fetching article details when articleId is provided and the screen is not in edit mode
    LaunchedEffect(articleId) {
        if (articleId != null && !isEditMode) {
            viewModel.fetchArticleDetails(articleId)
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (isEditMode) {
            viewModel.imageUri.value = uri
        }
    }

    // Listen for changes in article state and update UI
    LaunchedEffect(viewModel.article.value) {
        val article = viewModel.article.value
        if (article != null) {
            viewModel.setTitle(article.title)
            viewModel.setSelectedCategory(article.category)
            viewModel.setContent(article.content)
        }
    }

    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("냉장고", "TV", "청소기", "에어컨", "전자레인지", "냉장고", "기타") // List of categories

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.setShowDialog(false) },
            confirmButton = {
                Button(onClick = {
                    viewModel.setShowDialog(false)
                    navController.navigate(BottomNavItem.FreeSharing.screen_route) // 페이지 이동
                }) {
                    Text("확인")
                }
            },
            title = { Text(text = "안내") },
            text = { Text("글 등록이 완료되었습니다.") }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(vertical = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image selection section
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(if (imageUri != null) Color.Transparent else Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            imageUri?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Text("사진 없음", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditMode) {
            Button(onClick = { launcher.launch("image/*") }) {
                Text("사진 등록")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title Text Field
        OutlinedTextField(
            value = title,
            onValueChange = { if (isEditMode) viewModel.setTitle(it) else article?.title },
            label = { Text("제목") },
            readOnly = !isEditMode,
            modifier = Modifier.fillMaxWidth()  // Fills the available width
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = { },
                readOnly = !isEditMode,
                label = { Text("카테고리") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isEditMode,
                        onClick = {
                            if (isEditMode) {
                                viewModel.setSelectedCategory(category)
                            }
                            expanded = false
                        }
                    )
                }
            }
        }

        // Price/Text Content Text Field
        OutlinedTextField(
            value = content,
            onValueChange = { if (isEditMode) viewModel.setContent(it) },
            label = { Text("글 내용") },
            readOnly = !isEditMode,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Register Button
        if (isEditMode) {
            Button(
                onClick = {
//                    viewModel.onRegisterButtonClick()
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("나눔글 등록")
            }
        }
    }
}
