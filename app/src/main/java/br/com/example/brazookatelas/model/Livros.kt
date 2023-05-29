package br.com.example.brazookatelas.model

import java.util.*

data class Livros(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val sinopse: String,
    val autor: String,
    val ano: Int,
    val alvo: String,
    val genero: String,
    val capa: String? = null,
    val paginas: Int,
)