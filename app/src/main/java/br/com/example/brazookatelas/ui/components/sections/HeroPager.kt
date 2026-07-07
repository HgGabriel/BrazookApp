@file:OptIn(ExperimentalFoundationApi::class)
package br.com.example.brazookatelas.ui.components.sections

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.theme.LocalCategoryAccent
import br.com.example.brazookatelas.ui.theme.OverlayTextPrimary
import br.com.example.brazookatelas.ui.theme.OverlayTextSecondary
import br.com.example.brazookatelas.ui.components.BrazookImage
import kotlin.math.absoluteValue

@Composable
fun HeroPager(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier,
    badgeLabel: String? = null
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState()

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            pageCount = items.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 48.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) { page ->
            val item = items[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                        val scale = lerp(
                            start = 0.90f,
                            stop = 1.0f,
                            fraction = 1.0f - pageOffset.coerceIn(0.0f, 1.0f)
                        )
                        scaleX = scale
                        scaleY = scale
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1.0f,
                            fraction = 1.0f - pageOffset.coerceIn(0.0f, 1.0f)
                        )
                    }
                    .clickable { onItemClick(item) }
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Capa/Poster
                        BrazookImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Overlay cinematográfico de 3 stops
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        0.0f to Color.Transparent,
                                        0.5f to Color.Black.copy(alpha = 0.1f),
                                        1.0f to Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                        )

                        // Badge de destaque (opcional, definida por categoria)
                        if (badgeLabel != null) {
                            val accent = LocalCategoryAccent.current
                            Surface(
                                color = accent.color.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = null,
                                        tint = accent.onColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = badgeLabel,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = accent.onColor
                                    )
                                }
                            }
                        }

                        // Textos Informativos
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = OverlayTextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.genre + "  •  " + item.year,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OverlayTextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Pill-shaped page indicator (estilo Netflix/Spotify)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(items.size) { index ->
                val selected = pagerState.currentPage == index
                val width = animateDpAsState(
                    targetValue = if (selected) 24.dp else 8.dp,
                    animationSpec = spring(dampingRatio = 0.7f, stiffness = 300f),
                    label = "indicatorWidth"
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(6.dp)
                        .width(width.value)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            color = if (selected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}
