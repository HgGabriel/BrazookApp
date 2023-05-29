package br.com.example.brazookatelas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.example.brazookatelas.route.AppRouteName
import br.com.example.brazookatelas.sampledata.sampleFilmes
import br.com.example.brazookatelas.ui.components.sections.filmes.DetailScreenFilmes
import br.com.example.brazookatelas.ui.screens.FilmesScreen
import br.com.example.brazookatelas.ui.screens.JogosScreen
import br.com.example.brazookatelas.ui.screens.LivrosScreen
import br.com.example.brazookatelas.ui.screens.SeriesScreen

object AppRoute {

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = AppRouteName.Filmes) {
            composable(AppRouteName.Filmes) {
                FilmesScreen()
            }
            composable(AppRouteName.Livros) {
                LivrosScreen()
            }
            composable(AppRouteName.Jogos) {
                JogosScreen()
            }
            composable(AppRouteName.Series) {
                SeriesScreen()
            }
            composable(
                "${AppRouteName.DetailFilmes}/{filmeId}"
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("filmeId")
                sampleFilmes.find {
                    it.id == id
                }?.let { filmes ->
                    DetailScreenFilmes(
                        filmes = filmes
                    )
                }
            }
        }
    }
}

//fun findIndex(arr: List<Filmes>,item: Int): Int{
//    return arr.indexOf(item)
//}