package br.com.example.brazookatelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.example.brazookatelas.route.AppRoute.Navigation
import br.com.example.brazookatelas.model.BottomNavItem
import br.com.example.brazookatelas.route.AppRouteName
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            BrazookaTelasTheme {
                Box(modifier = Modifier.background(brush = gradient)) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Filmes",
                                        route = AppRouteName.Filmes,
                                        icon = Icons.Default.Movie
                                    ),
                                    BottomNavItem(
                                        name = "Series",
                                        route = AppRouteName.Series,
                                        icon = Icons.Default.Tv
                                    ),
                                    BottomNavItem(
                                        name = "Livros",
                                        route = AppRouteName.Livros,
                                        icon = Icons.Default.Book
                                    ),
                                    BottomNavItem(
                                        name = "Jogos",
                                        route = AppRouteName.Jogos,
                                        icon = Icons.Default.VideogameAsset
                                    ),
                                ),
                                navController = navController,
                                onItemClick = {
                                    val route = it.route
                                    navController.navigate(route){
                                        launchSingleTop = true
                                        popUpTo(route)
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Navigation(navController = navController)
                        }
                    }
                }

            }

        }
    }

}

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Black.copy(alpha = 0.1f),
        elevation = 2.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {

                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background( Color.Black.copy(alpha = 0.0f))) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )

                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }

                    }
                }
            )
        }
    }
}


val gradient = Brush.linearGradient(
    0.0f to Color(0xff000000),
    0.41f to Color(0xff0088ce),
    1.0f to Color(0xff00ff51),
//    0.50f to Color(0xff00d76a),
//    0.85f to Color(0xff00be3e),
    start = Offset.Zero,
    end = Offset.Infinite
)

