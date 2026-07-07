package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent

/**
 * Seletor de temporadas em pílulas — assinatura visual de Séries. Cada botão tem física de mola
 * (0.96f ao toque) e a temporada selecionada é preenchida com o acento de categoria.
 */
@Composable
fun SeasonSelectorTabs(
    totalTemporadas: Int,
    selectedTemporada: Int,
    onSeasonSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (totalTemporadas <= 1) return
    val accent = LocalCategoryAccent.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(totalTemporadas) { index ->
            val temporada = index + 1
            SeasonTab(
                temporada = temporada,
                selected = temporada == selectedTemporada,
                accentColor = accent.color,
                onColor = accent.onColor,
                onClick = { onSeasonSelected(temporada) }
            )
        }
    }
}

@Composable
private fun SeasonTab(
    temporada: Int,
    selected: Boolean,
    accentColor: Color,
    onColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "seasonTabScale"
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) accentColor else MaterialTheme.colorScheme.surfaceVariant,
        label = "seasonTabBackground"
    )
    val contentColor = if (selected) onColor else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .heightIn(min = 48.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Temp. $temporada",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )
        }
    }
}
