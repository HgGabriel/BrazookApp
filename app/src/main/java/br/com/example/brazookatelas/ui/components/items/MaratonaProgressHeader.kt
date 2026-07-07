@file:OptIn(ExperimentalAnimationApi::class)
package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.MotionEaseStandard

/**
 * Cabeçalho de progresso de maratona. `material3:1.0.1` (versão fixada no projeto) não expõe
 * `strokeCap` em `LinearProgressIndicator`, então os cantos arredondados da barra são desenhados
 * manualmente com `Canvas` em vez de assumir uma API mais nova.
 */
@Composable
fun MaratonaProgressHeader(
    watchedCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    val accent = LocalCategoryAccent.current
    val progress by animateFloatAsState(
        targetValue = if (totalCount > 0) watchedCount.toFloat() / totalCount else 0f,
        animationSpec = tween(durationMillis = 300, easing = MotionEaseStandard),
        label = "maratonaProgress"
    )
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Visto: ",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            AnimatedContent(
                targetState = watchedCount,
                transitionSpec = {
                    (slideInVertically { it / 2 } + fadeIn()) with (slideOutVertically { -it / 2 } + fadeOut())
                },
                label = "watchedCount"
            ) { count ->
                Text(
                    text = "$count",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = accent.color
                )
            }
            Text(
                text = " de $totalCount episódios",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        ) {
            val cornerRadius = CornerRadius(size.height / 2f, size.height / 2f)
            drawRoundRect(
                color = trackColor,
                size = size,
                cornerRadius = cornerRadius
            )
            drawRoundRect(
                color = accent.color,
                size = Size(size.width * progress.coerceIn(0f, 1f), size.height),
                cornerRadius = cornerRadius
            )
        }
    }
}
