package br.com.example.brazookatelas.model

import java.io.Serializable

interface MediaItem : Serializable {
    val id: String
    val title: String
    val description: String
    val genre: String
    val rating: Double
    val imageUrl: String?
    val year: Int
    val classification: String
    val subTitle: String? get() = null
    val detailsText: String? get() = null
    val badgesList: List<String> get() = emptyList()
}
