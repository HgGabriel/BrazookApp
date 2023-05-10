package br.com.example.brazookatelas.model

data class Series(
    val nome: String,
    val sinopse: String,
    val genero: String,
    val temporadas: String,
    val episodios: String,
    val classificacao: String,
    val atores: String,
    val nota: Double,
    val poster: String? = null,
    val ano: Int
)