@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent

/** Mensagem motivacional dinâmica de acordo com a fração de páginas já lidas. */
private fun motivationalMessage(percent: Int): String = when {
    percent >= 100 -> "Concluído! Leitura finalizada"
    percent >= 75 -> "Reta final — quase lá!"
    percent >= 40 -> "No caminho certo!"
    percent > 0 -> "Boa, você começou!"
    else -> "Comece sua leitura arrastando o marcador"
}

/**
 * Rastreador de leitura interativo — assinatura visual de Livros. O usuário arrasta o slider
 * para marcar quantas páginas já leu; o progresso é persistido via [onPagesReadChange]
 * (ver [br.com.example.brazookatelas.ui.viewmodel.LivrosProgressViewModel]), sobrevivendo à
 * navegação de volta. Dispara um tick háptico a cada 10% de progresso durante o arraste,
 * não apenas ao soltar, para reforçar a sensação física de "passar página".
 */
@Composable
fun ReadingTracker(
    totalPaginas: Int,
    pagesRead: Int,
    onPagesReadChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (totalPaginas <= 0) return
    val accent = LocalCategoryAccent.current
    val haptic = LocalHapticFeedback.current
    var lastDecile by remember(totalPaginas) { mutableStateOf(pagesRead * 10 / totalPaginas) }

    val percent = (pagesRead * 100 / totalPaginas).coerceIn(0, 100)

    Column(modifier = modifier.fillMaxWidth()) {
        Slider(
            value = pagesRead.toFloat(),
            onValueChange = { newValue ->
                val pages = newValue.toInt().coerceIn(0, totalPaginas)
                val decile = pages * 10 / totalPaginas
                if (decile != lastDecile) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    lastDecile = decile
                }
                onPagesReadChange(pages)
            },
            valueRange = 0f..totalPaginas.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = accent.color,
                activeTrackColor = accent.color,
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        AnimatedContent(
            targetState = percent,
            transitionSpec = { fadeIn() with fadeOut() },
            label = "readingTrackerMessage"
        ) { currentPercent ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (currentPercent >= 100) {
                    Icon(
                        imageVector = Icons.Rounded.EmojiEvents,
                        contentDescription = null,
                        tint = accent.color,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = "Você leu $pagesRead de $totalPaginas págs. ($currentPercent%) — ${motivationalMessage(currentPercent)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (currentPercent >= 100) FontWeight.SemiBold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
