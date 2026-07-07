package br.com.example.brazookatelas.ui.components.badges

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.ui.theme.ClassInd10
import br.com.example.brazookatelas.ui.theme.ClassInd12
import br.com.example.brazookatelas.ui.theme.ClassInd14
import br.com.example.brazookatelas.ui.theme.ClassInd16
import br.com.example.brazookatelas.ui.theme.ClassInd18
import br.com.example.brazookatelas.ui.theme.ClassIndLivre
import br.com.example.brazookatelas.ui.theme.ClassIndOnDark
import br.com.example.brazookatelas.ui.theme.ClassIndOnLight
import br.com.example.brazookatelas.ui.theme.ClassIndUnknown

/**
 * Badge de classificação indicativa nacional, compartilhada por todas as telas de detalhe
 * e cards de grid. Usa os pictogramas oficiais do ClassInd quando disponíveis no res/drawable;
 * cai para um selo textual colorido apenas se o drawable não existir.
 */
@Composable
fun AgeRatingBadge(classification: String) {
    val drawableId = when (classification.trim().uppercase()) {
        "L" -> R.drawable.idadelivre
        "10" -> R.drawable.idadedez
        "12" -> R.drawable.idadedoze
        "14" -> R.drawable.idadequatorze
        "16" -> R.drawable.idadedezesseis
        "18" -> R.drawable.idadedezoito
        else -> null
    }

    if (drawableId != null) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = "Classificação $classification",
            modifier = Modifier.size(24.dp)
        )
    } else {
        val background = when (classification.trim().uppercase()) {
            "L" -> ClassIndLivre
            "10" -> ClassInd10
            "12" -> ClassInd12
            "14" -> ClassInd14
            "16" -> ClassInd16
            "18" -> ClassInd18
            else -> ClassIndUnknown
        }
        val onBackground = if (classification.trim().uppercase() == "12") ClassIndOnDark else ClassIndOnLight

        Box(
            modifier = Modifier
                .background(color = background, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = classification,
                color = onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
