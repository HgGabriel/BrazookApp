@file:OptIn(ExperimentalMaterial3Api::class)
package br.com.example.brazookatelas.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.R
import br.com.example.brazookatelas.model.MediaItem
import br.com.example.brazookatelas.ui.theme.FavoriteRed
import br.com.example.brazookatelas.ui.theme.GlassBorderDark
import br.com.example.brazookatelas.ui.theme.GlassBorderLight
import br.com.example.brazookatelas.ui.theme.GlassSurfaceDark
import br.com.example.brazookatelas.ui.theme.GlassSurfaceLight
import br.com.example.brazookatelas.ui.components.BrazookImage

@Composable
fun MediaDetailScreen(
    item: MediaItem,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorited by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFavorited) 1.05f else 1.0f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "fabScale"
    )

    // Animação de entrada escalonada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isFavorited = !isFavorited },
                modifier = Modifier
                    .scale(scale)
                    .height(56.dp),
                containerColor = if (isFavorited) FavoriteRed else MaterialTheme.colorScheme.primary,
                contentColor = if (isFavorited) Color.White else MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(28.dp),
                icon = {
                    Icon(
                        imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = if (isFavorited) "Favoritado" else "Favoritar",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Detalhes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Hero Section: Imagem + Info Cards
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { it / 4 }
            ) {
                Row(
                    modifier = Modifier
                        .height(280.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    // Poster/Capa
                    BrazookImage(
                        model = item.imageUrl,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.width(20.dp))

                    // Info Cards com glassmorphism
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MediaInfoCard(
                            imageVector = Icons.Default.Category,
                            title = "Gênero",
                            value = item.genre
                        )
                        MediaInfoCard(
                            imageVector = Icons.Default.CalendarMonth,
                            title = "Ano",
                            value = item.year.toString()
                        )
                        MediaInfoCard(
                            imageVector = Icons.Default.Star,
                            title = "Nota",
                            value = "%.1f".format(item.rating)
                        )
                    }
                }
            }

            // Título
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            // Chips de gênero
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item.genre.split(",", "/").map { it.trim() }.filter { it.isNotBlank() }.forEach { genre ->
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = genre,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        border = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sinopse
            Text(
                text = "Sinopse",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )

            Spacer(modifier = Modifier.height(90.dp)) // Espaço para o FAB
        }
    }
}

@Composable
fun MediaInfoCard(
    imageVector: ImageVector,
    title: String,
    value: String
) {
    val isDark = isSystemInDarkTheme()
    val glassBg = if (isDark) GlassSurfaceDark else GlassSurfaceLight
    val glassBorder = if (isDark) GlassBorderDark else GlassBorderLight

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = glassBg,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = glassBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
