package br.com.example.brazookatelas.model

data class Livros(
    val nome: String,
    val sinopse: String,
    val autor: String,
    val ano: Int,
    val alvo: String,
    val genero: String,
    val capa: String? = null,
    val paginas: Int,
)