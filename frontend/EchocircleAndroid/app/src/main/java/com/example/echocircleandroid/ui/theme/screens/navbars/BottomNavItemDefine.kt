import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.echocircleandroid.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val screen_route: String
) {
    object DirectProcessing : BottomNavItem( R.string.direct_processing, Icons.Default.Search, "direct_processing")
    object HomeCollection : BottomNavItem(R.string.app_collection, Icons.Default.Home, "home_collection")
    object FreeSharing : BottomNavItem(R.string.free_sharing, Icons.Default.Favorite, "free_sharing")
    object MyPage : BottomNavItem( R.string.my_page, Icons.Default.Person, "my_page")
}
