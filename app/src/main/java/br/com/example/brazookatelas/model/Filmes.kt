package br.com.example.brazookatelas.model

import java.util.*

data class Filmes(
    val id: String = UUID.randomUUID().toString(),
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