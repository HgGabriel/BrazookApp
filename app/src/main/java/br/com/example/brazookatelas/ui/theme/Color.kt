package br.com.example.brazookatelas.ui.theme

import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════════════
// TEMA CLARO — Identidade Brasileira Premium
// ═══════════════════════════════════════════════════════════════════════

// Primárias & Acentos
val LightPrimary = Color(0xFF0E5E3A)           // Verde Amazônia Profundo
val LightOnPrimary = Color(0xFFFFFFFF)          // Branco puro para contraste sobre verde
val LightPrimaryContainer = Color(0xFFC8F0D8)   // Verde pastel suave para containers selecionados
val LightOnPrimaryContainer = Color(0xFF00210E)  // Verde profundíssimo

// Secundárias
val LightSecondary = Color(0xFFD97706)          // Amarelo Ouro Solar
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFFFF0D4) // Ouro pastel claro
val LightOnSecondaryContainer = Color(0xFF3D2300)

// Terciárias
val LightTertiary = Color(0xFF0F4C81)           // Azul Oceano Profundo
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFD1E4FF)  // Azul pastel para chips/tags
val LightOnTertiaryContainer = Color(0xFF001D36)

// Superfícies
val LightBackground = Color(0xFFFAF9F6)         // Areia Suave / Off-white
val LightOnBackground = Color(0xFF1E293B)        // Slate Charcoal (alta legibilidade)
val LightSurface = Color(0xFFFFFFFF)             // Branco puro para cards/superfícies
val LightOnSurface = Color(0xFF0F172A)           // Slate Charcoal Escuro
val LightSurfaceVariant = Color(0xFFF1F5F0)      // Verde-cinza claro para cards secundários
val LightOnSurfaceVariant = Color(0xFF414942)    // Texto em superfícies variantes

// Contornos
val LightOutline = Color(0xFFC1C9C2)             // Borda neutra suave com tom verde
val LightOutlineVariant = Color(0xFFDDE5DD)       // Borda mais sutil (dividers)

// Error
val LightError = Color(0xFFBA1A1A)
val LightOnError = Color(0xFFFFFFFF)
val LightErrorContainer = Color(0xFFFFDAD6)
val LightOnErrorContainer = Color(0xFF410002)

// Inversos & Scrim
val LightInverseSurface = Color(0xFF2F312F)
val LightInverseOnSurface = Color(0xFFF0F1ED)
val LightInversePrimary = Color(0xFF7FD9A4)
val LightScrim = Color(0xFF000000)

// Gradiente Premium de Fundo
val PremiumLightGradient = listOf(
    Color(0xFFFFFFFF),                            // Branco Puro no topo
    Color(0xFFF0F5F2),                            // Toque sutil de verde menta
    Color(0xFFE3EFE8)                             // Verde folha ultra-suave na base
)

// ═══════════════════════════════════════════════════════════════════════
// TEMA ESCURO — Identidade Brasileira de Alta Tecnologia (Obsidian Amazon)
// ═══════════════════════════════════════════════════════════════════════

// Primárias & Acentos
val DarkPrimary = Color(0xFF10B981)              // Verde Esmeralda elegante
val DarkOnPrimary = Color(0xFF003823)            // Contraste escuro sobre esmeralda
val DarkPrimaryContainer = Color(0xFF005235)     // Container de acento escuro floresta
val DarkOnPrimaryContainer = Color(0xFF6EE7B7)   // Verde menta claro

// Secundárias
val DarkSecondary = Color(0xFFF59E0B)            // Ouro Solar quente
val DarkOnSecondary = Color(0xFF452700)
val DarkSecondaryContainer = Color(0xFF6B3C00)
val DarkOnSecondaryContainer = Color(0xFFFEF3C7)

// Terciárias
val DarkTertiary = Color(0xFF06B6D4)             // Azul Oceano / Ciano
val DarkOnTertiary = Color(0xFF003640)
val DarkTertiaryContainer = Color(0xFF004F5D)
val DarkOnTertiaryContainer = Color(0xFFCFFAFE)

// Superfícies
val DarkBackground = Color(0xFF080F0B)           // Obsidian Floresta profundo (quase preto)
val DarkOnBackground = Color(0xFFE2E8F0)         // Branco acinzentado (alta legibilidade)
val DarkSurface = Color(0xFF111A15)              // Superfície floresta escura elevada
val DarkOnSurface = Color(0xFFF8FAFC)            // Off-white brilhante
val DarkSurfaceVariant = Color(0xFF1A2520)       // Superfície variante (cards)
val DarkOnSurfaceVariant = Color(0xFFC0C9C4)     // Texto secundário / sutil

// Contornos
val DarkOutline = Color(0xFF3B4841)              // Borda visível no escuro floresta
val DarkOutlineVariant = Color(0xFF2B3831)        // Borda sutil (dividers)

// Error
val DarkError = Color(0xFFFFB4AB)
val DarkOnError = Color(0xFF690005)
val DarkErrorContainer = Color(0xFF93000A)
val DarkOnErrorContainer = Color(0xFFFFDAD6)

// Inversos & Scrim
val DarkInverseSurface = Color(0xFFE2E8F0)
val DarkInverseOnSurface = Color(0xFF2F312F)
val DarkInversePrimary = Color(0xFF0E5E3A)
val DarkScrim = Color(0xFF000000)

// Gradiente Premium de Fundo
val PremiumDarkGradient = listOf(
    Color(0xFF080F0B),
    Color(0xFF051C12),
    Color(0xFF022B1B)                            // Glow de verde floresta sutil na base
)

// ═══════════════════════════════════════════════════════════════════════
// CORES DECORATIVAS / ACENTOS ESPECÍFICOS (usadas em componentes)
// ═══════════════════════════════════════════════════════════════════════

/** Cor da estrela de avaliação / rating */
val StarGold = Color(0xFFF59E0B)

/** Cor do botão de favoritar quando ativo */
val FavoriteRed = Color(0xFFEF4444)

/** Texto sobre overlay escuro (hero, pagers, blurry cards) */
val OverlayTextPrimary = Color(0xFFF8FAFC)
val OverlayTextSecondary = Color(0xFFCBD5E1)

/** Glassmorphism card background */
val GlassSurfaceLight = Color(0x33FFFFFF)   // 20% white
val GlassSurfaceDark = Color(0x33111A15)    // 20% dark surface
val GlassBorderLight = Color(0x55FFFFFF)    // 33% white
val GlassBorderDark = Color(0x553B4841)     // 33% outline dark

// ═══════════════════════════════════════════════════════════════════════
// CLASSIFICAÇÃO INDICATIVA (ClassInd) — fallback textual quando o
// pictograma oficial (AgeRatingBadge) não está disponível para a categoria
// ═══════════════════════════════════════════════════════════════════════

val ClassIndLivre = Color(0xFF4CAF50)
val ClassInd10 = Color(0xFF03A9F4)
val ClassInd12 = Color(0xFFFFEB3B)
val ClassInd14 = Color(0xFFFF9800)
val ClassInd16 = Color(0xFFF44336)
val ClassInd18 = Color(0xFF000000)
val ClassIndUnknown = Color(0xFF9E9E9E)

val ClassIndOnLight = Color(0xFFFFFFFF)
val ClassIndOnDark = Color(0xFF000000)

// ═══════════════════════════════════════════════════════════════════════
// ACENTO DE CATEGORIA — Séries (Violeta, identidade nova sem equivalente
// no colorScheme base). Par claro/escuro com contraste verificado (>4.5:1
// em ambos os sentidos), seguindo a mesma lógica tonal de LightPrimary/DarkPrimary.
// ═══════════════════════════════════════════════════════════════════════

val SeriesAccentLight = Color(0xFF7C3AED)   // Violeta profundo — contraste em fundo claro
val SeriesAccentOnLight = Color(0xFFFFFFFF)
val SeriesAccentDark = Color(0xFFC4B5FD)    // Violeta claro/dessaturado — contraste em fundo escuro
val SeriesAccentOnDark = Color(0xFF2E1065)

// ═══════════════════════════════════════════════════════════════════════
// ACENTO DE CATEGORIA — Livros (Terracota, identidade nova sem equivalente
// no colorScheme base). Par claro/escuro com contraste verificado (>4.5:1
// em ambos os sentidos), seguindo a mesma lógica tonal de LightPrimary/DarkPrimary.
// ═══════════════════════════════════════════════════════════════════════

val LivrosAccentLight = Color(0xFF9A3412)  // Terracota profundo — contraste em fundo claro/sépia
val LivrosAccentOnLight = Color(0xFFFFFFFF)
val LivrosAccentDark = Color(0xFFFDBA74)   // Terracota clara/pêssego — contraste em fundo escuro
val LivrosAccentOnDark = Color(0xFF4A1D0A)

// ═══════════════════════════════════════════════════════════════════════
// LIVROS — Superfícies de papel/sépia (Atmosfera "Aconchego & Biblioteca")
// ═══════════════════════════════════════════════════════════════════════

val LivrosPaperLight = Color(0xFFFAF6F0)
val LivrosPaperDark = Color(0xFF1E1A16)

// Prateleira física (VirtualShelf) — madeira e sombra projetada sob o carrossel "Mais Lidos"
val ShelfWoodLight = Color(0xFFB08968)
val ShelfWoodDark = Color(0xFF3E2A1F)
val ShelfShadowLight = Color(0x33000000)
val ShelfShadowDark = Color(0x66000000)

// Badge de Público-Alvo (Livros) — pastéis semânticos por faixa etária
val AudienceInfantilLight = Color(0xFFD1E4FF)
val AudienceInfantilOnLight = Color(0xFF001D36)
val AudienceInfantilDark = Color(0xFF04364D)
val AudienceInfantilOnDark = Color(0xFFCFE5FF)

val AudienceJovemLight = Color(0xFFFFDCC7)
val AudienceJovemOnLight = Color(0xFF7A2E0E)
val AudienceJovemDark = Color(0xFF5A2410)
val AudienceJovemOnDark = Color(0xFFFFD6BE)

val AudienceAdultoLight = Color(0xFFE7E4DD)
val AudienceAdultoOnLight = Color(0xFF3D3929)
val AudienceAdultoDark = Color(0xFF3A362C)
val AudienceAdultoOnDark = Color(0xFFE6E1D6)

// ═══════════════════════════════════════════════════════════════════════
// JOGOS — Badges de marca oficiais do painel "Onde Jogar". Cores fixas por
// marca (não variam por tema, mesmo tratamento do ClassInd acima) — o acento
// de categoria de Jogos em si usa `DarkTertiary`/`LightTertiary` já existentes
// via `MaterialTheme.colorScheme.tertiary` (ver `motor_design.md`, item 1),
// nunca estas cores de marca.
// ═══════════════════════════════════════════════════════════════════════

val PlatformPC = Color(0xFF0891B2)             // Ciano petróleo — distinto do DarkTertiary ambiente
val PlatformPlayStation = Color(0xFF003791)    // Azul Sony oficial
val PlatformXbox = Color(0xFF107C10)           // Verde Xbox oficial
val PlatformSwitch = Color(0xFFC4001A)         // Vermelho Switch (aprofundado para contraste com texto branco)
val PlatformMobile = Color(0xFF4F46E5)         // Índigo genérico (Android + iOS agrupados)
val PlatformUnknown = Color(0xFF64748B)
val PlatformBadgeOnColor = Color(0xFFFFFFFF)