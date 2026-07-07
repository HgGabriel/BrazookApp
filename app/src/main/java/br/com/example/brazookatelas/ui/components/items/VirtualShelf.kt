package br.com.example.brazookatelas.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.shelfShadowColor
import br.com.example.brazookatelas.ui.theme.shelfWoodColor

/**
 * Prateleira física decorativa em 2.5D exibida sob o carrossel "Mais Lidos & Recomendados" —
 * assinatura visual de Livros. Puramente ilustrativa (sem interação própria); a física de
 * "puxar o livro da estante" mora no press-state de cada [BookRowItem] acima dela.
 */
@Composable
fun VirtualShelf(modifier: Modifier = Modifier) {
    val wood = shelfWoodColor()
    val shadow = shelfShadowColor()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(
                    brush = Brush.verticalGradient(colors = listOf(wood, wood.copy(alpha = 0.75f))),
                    shape = RoundedCornerShape(2.dp)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(brush = Brush.verticalGradient(colors = listOf(shadow, shadow.copy(alpha = 0f))))
        )
    }
}
