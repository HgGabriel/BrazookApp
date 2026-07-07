package br.com.example.brazookatelas.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.example.brazookatelas.sampledata.*
import br.com.example.brazookatelas.ui.screens.*

object AppRoute {

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = AppRouteName.Filmes) {
            composable(AppRouteName.Filmes) {
                FilmesScreen(
                    onNavigateToDetails = { mediaId ->
                        navController.navigate("${AppRouteName.Detail}/filme/$mediaId")
                    }
                )
            }
            composable(AppRouteName.Series) {
                SeriesScreen(
                    onNavigateToDetails = { mediaId ->
                        navController.navigate("${AppRouteName.Detail}/serie/$mediaId")
                    }
                )
            }
            composable(AppRouteName.Livros) {
                LivrosScreen(
                    onNavigateToDetails = { mediaId ->
                        navController.navigate("${AppRouteName.Detail}/livro/$mediaId")
                    }
                )
            }
            composable(AppRouteName.Jogos) {
                JogosScreen(
                    onNavigateToDetails = { mediaId ->
                        navController.navigate("${AppRouteName.Detail}/jogo/$mediaId")
                    }
                )
            }
            composable(
                route = "${AppRouteName.Detail}/{mediaType}/{mediaId}",
                arguments = listOf(
                    navArgument("mediaType") { type = NavType.StringType },
                    navArgument("mediaId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val mediaType = backStackEntry.arguments?.getString("mediaType")
                val mediaId = backStackEntry.arguments?.getString("mediaId")

                // Lógica de busca temporária a partir dos samples locais
                when (mediaType) {
                    "filme" -> {
                        val filme = sampleFilmes.find { it.id == mediaId }
                        if (filme != null) {
                            FilmeDetailScreen(
                                filme = filme,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                    "serie" -> {
                        val serie = sampleSeries.find { it.id == mediaId }
                        if (serie != null) {
                            SeriesDetailScreen(
                                serie = serie,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                    "livro" -> {
                        val livro = sampleLivros.find { it.id == mediaId }
                        if (livro != null) {
                            LivrosDetailScreen(
                                livro = livro,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                    "jogo" -> {
                        val jogo = sampleJogos.find { it.id == mediaId }
                        if (jogo != null) {
                            JogoDetailScreen(
                                jogo = jogo,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}