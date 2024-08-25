import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.echocircleandroid.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val screen_route: String
) {
    data object DirectProcessing : BottomNavItem( R.string.direct_processing, Icons.Default.Search, "direct_processing")
    data object HomeCollection : BottomNavItem(R.string.app_collection, Icons.Default.Home, "home_collection")
    data object FreeSharing : BottomNavItem(R.string.free_sharing, Icons.Default.Favorite, R.string.community_main_screen.toString())
    data object MyPage : BottomNavItem( R.string.my_page, Icons.Default.Person, "my_page")
}

sealed class NavItem(
    @StringRes val title: Int,
    val screen_route:String
){
    data object FoundDeviceScreen : NavItem(R.string.found_device_screen, R.string.found_device_screen.toString())
    data object NotFoundDeviceScreen : NavItem(R.string.not_found_device_screen, R.string.not_found_device_screen.toString())
    data object CannotCollectDeviceScreen : NavItem(R.string.cannot_collect_device_screen,R.string.cannot_collect_device_screen.toString())
    data object CheckDeviceScreen : NavItem(R.string.check_device_screen,R.string.check_device_screen.toString())
    data object PhoneCallCollectScreen : NavItem(R.string.phoneCall_collect_screen,R.string.phoneCall_collect_screen.toString())

    data object CommunityMainScreen : NavItem(R.string.community_main_screen,R.string.community_main_screen.toString())
//    data object MemberLoginScreen : NavItem(R.string.member_login_screen,R.string.member_login_screen.toString())
}
