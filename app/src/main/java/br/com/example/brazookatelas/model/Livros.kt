package br.com.example.brazookatelas.model

import java.util.*
import kotlin.random.Random

data class Livros(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val sinopse: String,
    val autor: String,
    val ano: Int,
    val alvo: String,
    val genero: String,
    val nota: Double = Random.nextDouble(3.6, 5.0),
    val capa: String? = null,
    val paginas: Int,
):java.io.Serializable