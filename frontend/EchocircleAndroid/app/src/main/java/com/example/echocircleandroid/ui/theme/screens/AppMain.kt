

import androidx.compose.runtime.Composable


import androidx.navigation.compose.rememberNavController
import com.example.echocircleandroid.ui.theme.screens.components.AppMainScreen

enum class EchoCircleScreen(){

}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    AppMainScreen(navController = navController)
}
