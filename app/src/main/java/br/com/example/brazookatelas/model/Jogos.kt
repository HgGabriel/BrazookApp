package br.com.example.brazookatelas.model

import java.util.*
import kotlin.random.Random.Default.nextDouble


data class Jogos(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val genero: String,
    val classificacao: String,
    val estudio: String,
    val sinopse: String,
    val ano: Int,
    val nota: Double = nextDouble(3.6,5.0),
    val plataformas: String,
    val capa: String? = null,
        ):java.io.Serializable

