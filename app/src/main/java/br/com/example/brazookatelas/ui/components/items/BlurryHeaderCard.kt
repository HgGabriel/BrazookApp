package br.com.example.brazookatelas.ui.components.items

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.components.RatingBar
import br.com.example.brazookatelas.ui.components.badges.AgeRatingBadge
import br.com.example.brazookatelas.ui.theme.OverlayTextPrimary
import br.com.example.brazookatelas.ui.theme.OverlayTextSecondary
import br.com.example.brazookatelas.ui.theme.StarGold
import br.com.example.brazookatelas.ui.components.BrazookImage

@Composable
fun BlurryHeaderCard(
    item: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Scale-feedback
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "headerCardScale"
    )

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagem de fundo desfocada (Blur Backdrop)
            BrazookImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 16.dp,
                        radiusY = 16.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(16.dp))
                    ),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToScale(0.3f, 0.3f, 0.3f, 1.0f)
                })
            )

            // Overlay gradiente para melhor contraste
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Conteúdo Principal em Linha (Capa esquerda + Info direita)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Capa do Item (Esquerda)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .width(110.dp)
                        .fillMaxHeight()
                ) {
                    BrazookImage(
                        model = item.imageUrl,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Detalhes do Item (Direita)
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = OverlayTextPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "${item.subTitle} (${item.year})",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OverlayTextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Sinopse Curta
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = OverlayTextSecondary.copy(alpha = 0.85f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Informações de Classificação, Gênero e Nota no rodapé do card
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Badge de classificação indicativa com fallback em texto caso não encontre drawable
                        AgeRatingBadge(classification = item.classification)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "%.1f".format(item.rating),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                ),
                                color = StarGold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            RatingBar(
                                rating = item.rating / 2.0,
                                starsColor = StarGold,
                                starSize = 14.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
