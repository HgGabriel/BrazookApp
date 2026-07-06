package br.com.example.brazookatelas.model

import java.util.UUID

data class Series(
    override val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val sinopse: String,
    val genero: String,
    val temporadas: Int,
    val episodios: Int,
    val classificacao: String,
    val atores: String,
    val nota: Double,
    val poster: String? = null,
    val ano: Int
) : MediaItem {
    override val title: String get() = nome
    override val description: String get() = sinopse
    override val genre: String get() = genero
    override val rating: Double get() = nota
    override val imageUrl: String? get() = poster
    override val year: Int get() = ano
    override val classification: String get() = classificacao
    override val detailsText: String get() = "$temporadas temp. | $episodios eps."
}