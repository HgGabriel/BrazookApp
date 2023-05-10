package br.com.example.brazookatelas.model

data class Jogos(
    val nome: String,
    val genero: String,
    val classificacao: String,
    val estudio: String,
    val sinopse: String,
    val ano: Int,
    val plataformas: String,
    val capa: String? = null,
        )