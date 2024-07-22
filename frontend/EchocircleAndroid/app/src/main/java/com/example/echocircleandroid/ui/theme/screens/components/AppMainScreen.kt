package com.example.echocircleandroid.ui.theme.screens.components

import BottomNavigationBar
import FreeSharingScreen
import HomeCollectionScreen
import MyPageScreen
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
                HomeCollectionScreen()
            }
            composable(BottomNavItem.FreeSharing.screen_route) {
                FreeSharingScreen()
            }
            composable(BottomNavItem.MyPage.screen_route) {
                MyPageScreen()
            }
            composable("start_camera"){
                StartCameraScreen()
            }
        }
    }
}
