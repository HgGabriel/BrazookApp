package br.com.example.brazookatelas.model

import java.util.UUID
import kotlin.random.Random

data class Livros(
    override val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val sinopse: String,
    val autor: String,
    val ano: Int,
    val alvo: String,
    val genero: String,
    val nota: Double = Random.nextDouble(3.6, 5.0),
    val capa: String? = null,
    val paginas: Int
) : MediaItem {
    override val title: String get() = nome
    override val description: String get() = sinopse
    override val genre: String get() = genero
    override val rating: Double get() = nota
    override val imageUrl: String? get() = capa
    override val year: Int get() = ano
    override val classification: String get() = alvo
    override val subTitle: String get() = autor
    override val detailsText: String get() = "$paginas págs."
    override val badgesList: List<String> get() = listOf(alvo)
}