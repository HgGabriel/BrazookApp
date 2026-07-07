package br.com.example.brazookatelas.ui.components.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChildCare
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.ui.theme.AudienceAdultoDark
import br.com.example.brazookatelas.ui.theme.AudienceAdultoLight
import br.com.example.brazookatelas.ui.theme.AudienceAdultoOnDark
import br.com.example.brazookatelas.ui.theme.AudienceAdultoOnLight
import br.com.example.brazookatelas.ui.theme.AudienceInfantilDark
import br.com.example.brazookatelas.ui.theme.AudienceInfantilLight
import br.com.example.brazookatelas.ui.theme.AudienceInfantilOnDark
import br.com.example.brazookatelas.ui.theme.AudienceInfantilOnLight
import br.com.example.brazookatelas.ui.theme.AudienceJovemDark
import br.com.example.brazookatelas.ui.theme.AudienceJovemLight
import br.com.example.brazookatelas.ui.theme.AudienceJovemOnDark
import br.com.example.brazookatelas.ui.theme.AudienceJovemOnLight

private enum class AudienceTier(val icon: ImageVector, val label: String) {
    INFANTIL(Icons.Rounded.ChildCare, "Infantil"),
    JOVEM(Icons.Rounded.Face, "Jovem"),
    ADULTO(Icons.Rounded.Person, "Adulto")
}

private fun tierFor(alvo: String): AudienceTier {
    val normalized = alvo.trim().lowercase()
    return when {
        normalized.contains("infantil") -> AudienceTier.INFANTIL
        normalized.contains("jovem") -> AudienceTier.JOVEM
        else -> AudienceTier.ADULTO
    }
}

/**
 * Badge de público-alvo (`Livros.alvo`), com ícone vetorial e tom pastel semântico —
 * nunca o emoji de notação usado em `livros.md` (🧒/🧑/🧔), que não tinge nem escala como vetor.
 * Em [compact], mostra apenas o ícone (para selos pequenos sobre capas estreitas); do
 * contrário, inclui o rótulo textual (telas de detalhe e cartões mais largos).
 */
@Composable
fun AudienceBadge(alvo: String, modifier: Modifier = Modifier, compact: Boolean = false) {
    val tier = tierFor(alvo)
    val dark = isSystemInDarkTheme()
    val (background: Color, onBackground: Color) = when (tier) {
        AudienceTier.INFANTIL -> if (dark) AudienceInfantilDark to AudienceInfantilOnDark else AudienceInfantilLight to AudienceInfantilOnLight
        AudienceTier.JOVEM -> if (dark) AudienceJovemDark to AudienceJovemOnDark else AudienceJovemLight to AudienceJovemOnLight
        AudienceTier.ADULTO -> if (dark) AudienceAdultoDark to AudienceAdultoOnDark else AudienceAdultoLight to AudienceAdultoOnLight
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = background, shape = RoundedCornerShape(50))
            .padding(horizontal = if (compact) 4.dp else 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = tier.icon,
            contentDescription = tier.label,
            tint = onBackground,
            modifier = Modifier.size(14.dp)
        )
        if (!compact) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = tier.label,
                color = onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
