package com.example.echocircleandroid.ui.theme.screens.components.Community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun CommunityMainScreen(navController: NavController){

    var searchText by remember {
        mutableStateOf("")
    }

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

    ) {
        SearchBar(searchText = searchText, onValueChange = {searchText = it})
        Spacer(modifier = Modifier.height(8.dp))
        SharingCriteriaBar()

        CommunityCard(data = dummy)
    }

}
@Composable
fun SearchBar(searchText: String, onValueChange : (String) -> Unit){
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
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { /* 검색 버튼 클릭 시 동작 */ },
            modifier = Modifier
                .height(50.dp)
        ) {
            Text("검색")
        }
    }
}
@Composable
fun SharingCriteriaBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ){
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
            ) {
            Text("정렬 기준")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        ) {
            Text("나눔글 등록")
        }
    }
}
@Composable
fun CommunityCard(data: CommunityCardData){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(data.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
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

data class CommunityCardData(
    val imageUrl: String,
    val title: String,
    val category: String,
    val date: String,
    val isCompleted: Boolean
)