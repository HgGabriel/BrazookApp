package br.com.example.brazookatelas.model

data class Filmes(
    val filme: String,
    val sinopse: String,
    val genero: String,
    val classificacao: String,
    val atores: String,
    val roteiristas: String,
    val diretores: String,
    val nota: Double,
    val poster: String,
    val ano: Int
)