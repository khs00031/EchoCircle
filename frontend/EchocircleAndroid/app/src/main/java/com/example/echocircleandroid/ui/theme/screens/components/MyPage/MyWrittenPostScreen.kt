package com.example.echocircleandroid.ui.theme.screens.components.MyPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityCard
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityCardData

@Composable
fun MyWrittenPostScreen(navController: NavController) {

    val dummy = CommunityCardData(
        date = "2024.8.08",
        category = "세탁기",
        title = "삼성 세탁기 자겨가세용",
        imageUrl = "https://picsum.photos/250/250",
        isCompleted = true
    )

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

        CommunityCard(data = dummy)
    }
}