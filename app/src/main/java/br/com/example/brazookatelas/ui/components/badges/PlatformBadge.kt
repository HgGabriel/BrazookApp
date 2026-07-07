package br.com.example.brazookatelas.ui.components.badges

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.DeviceUnknown
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.SportsEsports
import androidx.compose.material.icons.rounded.VideogameAsset
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.GamePlatform
import br.com.example.brazookatelas.ui.theme.PlatformBadgeOnColor
import br.com.example.brazookatelas.ui.theme.PlatformMobile
import br.com.example.brazookatelas.ui.theme.PlatformPC
import br.com.example.brazookatelas.ui.theme.PlatformPlayStation
import br.com.example.brazookatelas.ui.theme.PlatformSwitch
import br.com.example.brazookatelas.ui.theme.PlatformUnknown
import br.com.example.brazookatelas.ui.theme.PlatformXbox

private data class PlatformStyle(val background: Color, val icon: ImageVector)

private fun styleFor(platform: GamePlatform): PlatformStyle = when (platform) {
    GamePlatform.PC -> PlatformStyle(PlatformPC, Icons.Rounded.Computer)
    GamePlatform.PLAYSTATION -> PlatformStyle(PlatformPlayStation, Icons.Rounded.SportsEsports)
    GamePlatform.XBOX -> PlatformStyle(PlatformXbox, Icons.Rounded.VideogameAsset)
    GamePlatform.SWITCH -> PlatformStyle(PlatformSwitch, Icons.Rounded.Gamepad)
    GamePlatform.MOBILE -> PlatformStyle(PlatformMobile, Icons.Rounded.Smartphone)
    GamePlatform.OUTRO -> PlatformStyle(PlatformUnknown, Icons.Rounded.DeviceUnknown)
}

/**
 * Badge de plataforma do painel "Onde Jogar" — cor de marca fixa (não deriva de
 * `LocalCategoryAccent`, ver `motor_design.md` item 1) e ícone vetorial do Material Icons no
 * lugar do emoji de notação usado em `jogos.md` (não há logotipo oficial de marca no conjunto
 * padrão do Compose; um `ImageVector` desenhado à mão traria risco de uso indevido de marca).
 * Ao tocar, dispara um leve tremor rotacional de 150ms como feedback tátil visual.
 */
@Composable
fun PlatformBadge(
    platform: GamePlatform,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val style = remember(platform) { styleFor(platform) }
    var clickTrigger by remember { mutableStateOf(0) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(clickTrigger) {
        if (clickTrigger > 0) {
            rotation.animateTo(-10f, tween(40))
            rotation.animateTo(10f, tween(60))
            rotation.animateTo(0f, tween(50))
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(min = 48.dp)
            .graphicsLayer { rotationZ = rotation.value }
            .background(color = style.background, shape = RoundedCornerShape(50))
            .let { base ->
                if (onClick != null) {
                    base.clickable {
                        clickTrigger++
                        onClick()
                    }
                } else {
                    base
                }
            }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = style.icon,
            contentDescription = platform.label,
            tint = PlatformBadgeOnColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = platform.label,
            color = PlatformBadgeOnColor,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}
