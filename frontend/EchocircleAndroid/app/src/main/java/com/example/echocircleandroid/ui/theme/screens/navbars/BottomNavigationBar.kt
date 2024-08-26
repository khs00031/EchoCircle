import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.echocircleandroid.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = listOf(
        BottomNavItem.DirectProcessing,
        BottomNavItem.HomeCollection,
        BottomNavItem.FreeSharing,
        BottomNavItem.MyPage
    )

    NavigationBar(
        containerColor = colorResource(id = R.color.green),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = {
                    Text(
                        stringResource(id = item.title),
                        fontWeight = if (currentRoute == item.screen_route) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = currentRoute == item.screen_route,
                onClick = {
                    // 현재 스택을 초기화하고 새로운 화면으로 이동
                    navController.navigate(item.screen_route) {
                        popUpTo(0) { inclusive = true } // 스택을 초기화
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = colorResource(id = R.color.dark_green)
                )
            )
        }
    }
}
