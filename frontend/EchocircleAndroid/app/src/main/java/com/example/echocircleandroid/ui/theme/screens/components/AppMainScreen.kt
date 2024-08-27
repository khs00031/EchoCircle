package com.example.echocircleandroid.ui.theme.screens.components

import BottomNavigationBar
import HomeCollectionScreen
import MyPageScreen
import StartCameraScreen
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.echocircleandroid.ui.theme.screens.LoginScreen
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityMainScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.CannotCollectDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.CheckDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.FoundDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.NotFoundDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.PhoneCallCollectScreen
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyApplianceScreen
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyPageViewModel
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyWrittenPostScreen
import com.example.echocircleandroid.ui.theme.screens.components.Member.MemberLoginScreen
import com.example.echocircleandroid.ui.theme.screens.components.Member.MemberRegistScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.GetTextWithCameraScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.InsertModelScreen
import com.example.echocircleandroid.ui.theme.screens.components.Product.SelectModelScreen
import com.example.echocircleandroid.ui.theme.screens.data.Product

@Composable
fun AppMainScreen(navController: NavHostController, startDestination: String) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "login" &&
                currentRoute != "member_login_screen" &&
                currentRoute != "member_regist_screen") {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("member_login_screen") {
                MemberLoginScreen(navController)
            }
            composable("member_regist_screen") {
                MemberRegistScreen(navController)
            }
            composable("product_collect_screen") {
                ProdcutCollectScreen(navController)
            }
            composable("get_text_with_camera_screen") {
                GetTextWithCameraScreen(navController)
            }
            composable(
                "insert_model_screen?capturedText={capturedText}",
                arguments = listOf(navArgument("capturedText") { nullable = true })
            ) { backStackEntry ->
                val capturedText = backStackEntry.arguments?.getString("capturedText") ?: ""
                InsertModelScreen(navController, serialNumber = capturedText)
            }
            composable(NavItem.CheckDeviceScreen.screen_route) { backStackEntry ->
                val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
                if (product != null) {
                    CheckDeviceScreen(navController, product)
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "제품 정보를 불러올 수 없습니다.", fontSize = 18.sp)
                    }
                }
            }
            composable("select_model_screen") {
                SelectModelScreen(navController)
            }
            composable(BottomNavItem.HomeCollection.screen_route) {
                HomeCollectionScreen(navController)
            }
            composable(BottomNavItem.FreeSharing.screen_route) {
                CommunityMainScreen(navController = navController)
            }
            composable(BottomNavItem.MyPage.screen_route) {
                MyPageScreen(navController, myPageViewModel = MyPageViewModel(application = Application()))
            }
            composable("my_written_posts") {
                MyWrittenPostScreen(navController, myPageViewModel = MyPageViewModel(application = Application()))
            }
            composable("my_appliances") {
                MyApplianceScreen(navController)
            }
            composable("start_camera") {
                StartCameraScreen(navController)
            }
            composable(NavItem.FoundDeviceScreen.screen_route) {
                FoundDeviceScreen(navController)
            }
            composable(NavItem.NotFoundDeviceScreen.screen_route) {
                NotFoundDeviceScreen(navController)
            }
            composable(NavItem.CannotCollectDeviceScreen.screen_route) {
                CannotCollectDeviceScreen(navController)
            }
            composable(NavItem.PhoneCallCollectScreen.screen_route) {
                PhoneCallCollectScreen()
            }
        }
    }
}
