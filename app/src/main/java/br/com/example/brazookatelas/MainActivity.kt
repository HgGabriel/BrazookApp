@file:OptIn(ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.example.brazookatelas.model.BottomNavItem
import br.com.example.brazookatelas.route.AppRoute
import br.com.example.brazookatelas.route.AppRouteName
import br.com.example.brazookatelas.ui.theme.BrazookaTelasTheme
import br.com.example.brazookatelas.ui.theme.LocalBackgroundGradient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            BrazookaTelasTheme {
                val navController = rememberNavController()
                val backgroundBrush = Brush.verticalGradient(colors = LocalBackgroundGradient.current)

                Box(modifier = Modifier.background(brush = backgroundBrush)) {
                    Scaffold(
                        containerColor = Color.Transparent, // Garante que o gradiente apareça sob o Scaffold
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem("Filmes", AppRouteName.Filmes, Icons.Default.Movie),
                                    BottomNavItem("Séries", AppRouteName.Series, Icons.Default.Tv),
                                    BottomNavItem("Livros", AppRouteName.Livros, Icons.Default.Book),
                                    BottomNavItem("Jogos", AppRouteName.Jogos, Icons.Default.VideogameAsset)
                                ),
                                navController = navController,
                                onItemClick = { item ->
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                        popUpTo(item.route)
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            AppRoute.Navigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                label = {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(text = item.name, style = MaterialTheme.typography.labelSmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
