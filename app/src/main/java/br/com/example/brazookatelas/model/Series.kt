package br.com.example.brazookatelas.model

import java.util.*

data class Series(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val sinopse: String,
    val genero: String,
    val temporadas: Int,
    val episodios: Int,
    val classificacao: String,
    val atores: String,
    val nota: Double,
    val poster: String? = null,
    val ano: Int
)