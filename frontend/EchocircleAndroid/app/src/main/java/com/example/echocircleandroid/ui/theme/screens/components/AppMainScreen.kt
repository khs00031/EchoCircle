package com.example.echocircleandroid.ui.theme.screens.components

import BottomNavigationBar
import FreeSharingScreen
import HomeCollectionScreen
import MyPageScreen
import NavItem
import StartCameraScreen
import UserGuideScreen
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

@Composable
fun AppMainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "login") {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login"){
                LoginScreen(navController)
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
                MyPageScreen()
            }
            composable("start_camera"){
                StartCameraScreen(navController)
            }

            composable(NavItem.FoundDeviceScreen.screen_route){
                FoundDeviceScreen(navController)
            }
            composable(NavItem.NotFoundDeviceScreen.screen_route){
                NotFoundDeviceScreen(navController)
            }
            composable(NavItem.CheckDeviceScreen.screen_route){
                CheckDeviceScreen(navController)
            }
            composable(NavItem.CannotCollectDeviceScreen.screen_route){
                CannotCollectDeviceScreen(navController)
            }
            composable(NavItem.PhoneCallCollectScreen.screen_route){
                PhoneCallCollectScreen()
            }

        }
    }
}
