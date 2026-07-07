package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import kotlinx.coroutines.delay

/**
 * Botão de play flutuante da tela de detalhes de filme. O círculo visível tem 56x56dp,
 * mas a área de toque é expandida para 64x64dp para garantir clique perfeito.
 * Ao tocar: háptica imediata, redução elástica de escala e um carregamento simulado de ~1s
 * antes de disparar [onPlayTrailer] (quem mostra o feedback de "reproduzindo").
 */
@Composable
fun PlayButtonFAB(onPlayTrailer: () -> Unit, modifier: Modifier = Modifier) {
    val accent = LocalCategoryAccent.current
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isLoading by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "playButtonScale"
    )

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1000)
            isLoading = false
            onPlayTrailer()
        }
    }

    Box(
        modifier = modifier
            .size(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !isLoading
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                isLoading = true
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(accent.color, accent.color.copy(alpha = 0.7f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = accent.onColor,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Reproduzir trailer",
                    tint = accent.onColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
