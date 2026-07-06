package br.com.example.brazookatelas.model

import java.util.UUID

data class Filmes(
    override val id: String = UUID.randomUUID().toString(),
    val filme: String,
    val sinopse: String,
    val genero: String,
    val classificacao: String,
    val atores: String,
    val roteiristas: String,
    val diretores: String,
    val nota: Double,
    val poster: String,
    val ano: Int
) : MediaItem {
    override val title: String get() = filme
    override val description: String get() = sinopse
    override val genre: String get() = genero
    override val rating: Double get() = nota
    override val imageUrl: String get() = poster
    override val year: Int get() = ano
    override val classification: String get() = classificacao
    override val subTitle: String get() = diretores
}