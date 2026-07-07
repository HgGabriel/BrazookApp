package br.com.example.brazookatelas.model

import java.util.UUID
import kotlin.random.Random

data class Jogos(
    override val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val genero: String,
    val classificacao: String,
    val estudio: String,
    val sinopse: String,
    val ano: Int,
    val nota: Double = Random.nextDouble(3.6, 5.0),
    val plataformas: String,
    val capa: String? = null
) : MediaItem {
    override val title: String get() = nome
    override val description: String get() = sinopse
    override val genre: String get() = genero
    override val rating: Double get() = nota
    override val imageUrl: String? get() = capa
    override val year: Int get() = ano
    override val classification: String get() = classificacao
    override val subTitle: String get() = estudio
    override val badgesList: List<String> get() = GamePlatform.parse(plataformas).map { it.label }
}
