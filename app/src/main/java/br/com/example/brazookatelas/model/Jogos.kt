package br.com.example.brazookatelas.model

import java.util.*

data class Jogos(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val genero: String,
    val classificacao: String,
    val estudio: String,
    val sinopse: String,
    val ano: Int,
    val plataformas: String,
    val capa: String? = null,
        )