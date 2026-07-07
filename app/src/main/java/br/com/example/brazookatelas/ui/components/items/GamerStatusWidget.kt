package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.GamerStatus
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.StarGold

/**
 * Seletor de status gamer — assinatura visual de Jogos. `SegmentedButton` só existe a partir de
 * material3 1.1.0 (`app/build.gradle` fixa 1.0.1, ver `motor_design.md` item 7); reproduzimos o
 * mesmo resultado visual com um `Row` de três `Surface` + `animateColorAsState`. "Jogando" usa
 * `LocalCategoryAccent` (ciano de Jogos) com respiração de brilho contínua; "Zerado!" dispara um
 * brilho dourado de explosão de ~300ms na primeira vez que é selecionado.
 */
@Composable
fun GamerStatusWidget(
    status: GamerStatus,
    onStatusChange: (GamerStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var previousStatus by remember { mutableStateOf(status) }
    val burst = remember { Animatable(0f) }

    LaunchedEffect(status) {
        if (status == GamerStatus.ZERADO && previousStatus != GamerStatus.ZERADO) {
            burst.snapTo(0f)
            burst.animateTo(1f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 260f))
        }
        previousStatus = status
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        GamerStatusButton(
            targetStatus = GamerStatus.QUERO_JOGAR,
            icon = Icons.Rounded.BookmarkBorder,
            label = "Quero Jogar",
            currentStatus = status,
            burstProgress = 0f,
            onSelect = onStatusChange,
            modifier = Modifier.weight(1f)
        )
        GamerStatusButton(
            targetStatus = GamerStatus.JOGANDO,
            icon = Icons.Rounded.PlayCircle,
            label = "Jogando",
            currentStatus = status,
            burstProgress = 0f,
            onSelect = onStatusChange,
            modifier = Modifier.weight(1f)
        )
        GamerStatusButton(
            targetStatus = GamerStatus.ZERADO,
            icon = Icons.Rounded.EmojiEvents,
            label = "Zerado!",
            currentStatus = status,
            burstProgress = burst.value,
            onSelect = onStatusChange,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GamerStatusButton(
    targetStatus: GamerStatus,
    icon: ImageVector,
    label: String,
    currentStatus: GamerStatus,
    burstProgress: Float,
    onSelect: (GamerStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = LocalCategoryAccent.current
    val haptic = LocalHapticFeedback.current
    val selected = currentStatus == targetStatus

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "gamerStatusPressScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "jogandoGlow")
    val breathe by infiniteTransition.animateFloat(
        initialValue = 0.65f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "jogandoGlowAlpha"
    )

    val targetBackground = when {
        selected && targetStatus == GamerStatus.JOGANDO -> accent.color.copy(alpha = breathe)
        selected && targetStatus == GamerStatus.ZERADO -> StarGold
        selected -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    }
    val background by animateColorAsState(targetValue = targetBackground, label = "gamerStatusBackground")

    val contentColor = when {
        selected && targetStatus == GamerStatus.JOGANDO -> accent.onColor
        selected && targetStatus == GamerStatus.ZERADO -> Color.Black
        selected -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    }

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        if (burstProgress > 0f) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .scale(0.6f + burstProgress * 0.9f)
                    .alpha(1f - burstProgress)
                    .background(Brush.radialGradient(listOf(StarGold.copy(alpha = 0.85f), Color.Transparent)))
            )
        }

        Surface(
            color = background,
            contentColor = contentColor,
            shape = RoundedCornerShape(14.dp),
            border = if (selected) {
                BorderStroke(1.5.dp, if (targetStatus == GamerStatus.ZERADO) StarGold else accent.color)
            } else {
                null
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .scale(pressScale)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    haptic.performHapticFeedback(
                        if (targetStatus == GamerStatus.ZERADO) HapticFeedbackType.LongPress else HapticFeedbackType.TextHandleMove
                    )
                    onSelect(targetStatus)
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1
                )
            }
        }
    }
}
