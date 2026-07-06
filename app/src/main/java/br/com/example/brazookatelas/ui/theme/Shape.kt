package br.com.example.brazookatelas.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Escala de formas M3 — cantos arredondados suaves, estilo premium content-first.
 *
 * Uso:
 * - extraSmall (4dp)  → chips, badges, tags
 * - small (8dp)       → text fields, botões pequenos
 * - medium (12dp)     → cards padrão, list items
 * - large (16dp)      → cards hero, bottom sheets
 * - extraLarge (28dp) → modais, FABs
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)