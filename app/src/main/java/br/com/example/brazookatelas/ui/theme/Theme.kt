package br.com.example.brazookatelas.ui.theme

import android.app.Activity
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ═══════════════════════════════════════════════════════════════════════
// COMPOSITION LOCALS
// ═══════════════════════════════════════════════════════════════════════

// CompositionLocal para expor o gradiente dinâmico do tema aos Composables (como o fundo da MainActivity)
val LocalBackgroundGradient = staticCompositionLocalOf<List<Color>> {
    emptyList()
}

/** Acento semântico de uma categoria (Filmes, Séries, Livros, Jogos), com seu par de contraste. */
data class CategoryAccent(val color: Color, val onColor: Color)

// CompositionLocal para expor o acento de categoria da tela atual (ex.: dourado de Filmes)
// sem hardcodar cores dentro dos componentes — cada *Screen provê o valor correto no topo da árvore.
val LocalCategoryAccent = staticCompositionLocalOf {
    CategoryAccent(Color.Unspecified, Color.Unspecified)
}

/** Acento violeta de Séries — par claro/escuro dedicado, pois não existe token equivalente no colorScheme base. */
@Composable
fun seriesAccent(): CategoryAccent = if (isSystemInDarkTheme()) {
    CategoryAccent(SeriesAccentDark, SeriesAccentOnDark)
} else {
    CategoryAccent(SeriesAccentLight, SeriesAccentOnLight)
}

/** Acento terracota de Livros — par claro/escuro dedicado, pois não existe token equivalente no colorScheme base. */
@Composable
fun livrosAccent(): CategoryAccent = if (isSystemInDarkTheme()) {
    CategoryAccent(LivrosAccentDark, LivrosAccentOnDark)
} else {
    CategoryAccent(LivrosAccentLight, LivrosAccentOnLight)
}

/** Tom de madeira da prateleira física (`VirtualShelf`) de Livros. */
@Composable
fun shelfWoodColor(): Color = if (isSystemInDarkTheme()) ShelfWoodDark else ShelfWoodLight

/** Sombra projetada sob a prateleira física (`VirtualShelf`) de Livros. */
@Composable
fun shelfShadowColor(): Color = if (isSystemInDarkTheme()) ShelfShadowDark else ShelfShadowLight

// ═══════════════════════════════════════════════════════════════════════
// ANIMATION TOKENS — Durações e easings consistentes em todo o app
// ═══════════════════════════════════════════════════════════════════════

/** Micro-interações: tap feedback, ripple */
const val MotionDurationShort = 150

/** Transições de estado: expansão, seleção */
const val MotionDurationMedium = 250

/** Transições complexas: modais, hero transitions */
const val MotionDurationLong = 350

/** Easing de entrada (elemento aparecendo) — ease-out */
val MotionEaseOut = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)

/** Easing de saída (elemento saindo) — ease-in */
val MotionEaseIn = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)

/** Easing padrão para transições bidirecionais */
val MotionEaseStandard = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

// ═══════════════════════════════════════════════════════════════════════
// COLOR SCHEMES
// ═══════════════════════════════════════════════════════════════════════

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    inverseSurface = LightInverseSurface,
    inverseOnSurface = LightInverseOnSurface,
    inversePrimary = LightInversePrimary,
    scrim = LightScrim
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    inverseSurface = DarkInverseSurface,
    inverseOnSurface = DarkInverseOnSurface,
    inversePrimary = DarkInversePrimary,
    scrim = DarkScrim
)

// ═══════════════════════════════════════════════════════════════════════
// THEME COMPOSABLE
// ═══════════════════════════════════════════════════════════════════════

@Composable
fun BrazookaTelasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val gradient = if (darkTheme) PremiumDarkGradient else PremiumLightGradient

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Usa a cor de fundo apropriada da superfície
            window.statusBarColor = if (darkTheme) DarkBackground.toArgb() else LightBackground.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()

            val insetsController = WindowCompat.getInsetsController(window, view)
            // Em tema escuro, ícones devem ser claros; em tema claro, escuros
            insetsController?.isAppearanceLightStatusBars = darkTheme
            insetsController?.isAppearanceLightNavigationBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalBackgroundGradient provides gradient) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = AppShapes,
            content = content
        )
    }
}