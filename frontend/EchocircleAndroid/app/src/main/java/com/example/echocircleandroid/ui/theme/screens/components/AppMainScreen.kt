package com.example.echocircleandroid.ui.theme.screens.components

import BottomNavigationBar
import HomeCollectionScreen
import MyPageScreen
import NavItem
import StartCameraScreen
import UserGuideScreen
import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.echocircleandroid.ui.theme.screens.LoginScreen
import com.example.echocircleandroid.ui.theme.screens.components.Community.CommunityMainScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.CannotCollectDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.CheckDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.FoundDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.NotFoundDeviceScreen
import com.example.echocircleandroid.ui.theme.screens.components.HomeCollect.PhoneCallCollectScreen
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyApplianceScreen
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyPageViewModel
import com.example.echocircleandroid.ui.theme.screens.components.MyPage.MyWrittenPostScreen
import com.example.echocircleandroid.ui.theme.screens.components.Member.MemberLoginScreen
import com.example.echocircleandroid.ui.theme.screens.components.Member.MemberRegistScreen

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
            composable("member_login_screen") {  // 회원 로그인 화면
                MemberLoginScreen(navController)
            }
            composable("member_regist_screen") {  // 회원가입 화면
                MemberRegistScreen(navController)
            }
            composable(BottomNavItem.DirectProcessing.screen_route) {
                UserGuideScreen(navController)
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
            composable(NavItem.CheckDeviceScreen.screen_route) {
                CheckDeviceScreen(navController)
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
