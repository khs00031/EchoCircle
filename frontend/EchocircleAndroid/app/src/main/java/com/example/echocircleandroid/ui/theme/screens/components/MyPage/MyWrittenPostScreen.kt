package com.example.echocircleandroid.ui.theme.screens.components.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityCard
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityCardData

@Composable
fun MyWrittenPostScreen(navController: NavController, myPageViewModel: MyPageViewModel) {
    val articleList by myPageViewModel.articleList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "내가 작성한 나눔글",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(Color(0xFFE0F7FA), RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!myPageViewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (articleList.isNotEmpty()) {
            // Use LazyColumn for efficient scrolling
            val communityCardDataList = articleList.map { article ->
                CommunityCardData(
                    imageUrl = article.thumbnail ?: "",
                    title = article.title,
                    category = article.category.toString(),
                    date = article.registTime ?: "",
                    isCompleted = article.shared
                )
            }

            LazyColumn {
                items(communityCardDataList) { cardData ->
                    CommunityCard(data = cardData)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "작성한 나눔글이 없습니다.",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}