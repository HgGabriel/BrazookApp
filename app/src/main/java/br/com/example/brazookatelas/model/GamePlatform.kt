package br.com.example.brazookatelas.model

/** Plataforma normalizada de um jogo, usada pelo painel "Onde Jogar" e pelo filtro da vitrine. */
enum class GamePlatform(val label: String) {
    PC("PC"),
    PLAYSTATION("PlayStation"),
    XBOX("Xbox"),
    SWITCH("Nintendo Switch"),
    MOBILE("Mobile"),
    OUTRO("Outro");

    companion object {
        private fun classify(token: String): GamePlatform {
            val normalized = token.lowercase()
            return when {
                normalized.contains("playstation") ||
                    normalized.contains("ps3") ||
                    normalized.contains("ps4") ||
                    normalized.contains("ps5") -> PLAYSTATION
                normalized.contains("xbox") -> XBOX
                normalized.contains("switch") || normalized.contains("nintendo") -> SWITCH
                normalized.contains("android") || normalized.contains("ios") || normalized.contains("mobile") -> MOBILE
                normalized.contains("pc") -> PC
                else -> OUTRO
            }
        }

        /**
         * `Jogos.plataformas` é texto livre separado por vírgulas e, antes do último item, pelo
         * conectivo " e " (ex.: "PC, PS4, Xbox One e Switch") — normaliza os dois separadores antes
         * de classificar, removendo pontuação residual, e deduplica preservando a ordem de
         * primeira ocorrência (ver `jogos.md`, seção "Revisão Crítica").
         */
        fun parse(raw: String): List<GamePlatform> {
            val normalized = raw.replace(Regex("\\s+[eE]\\s+"), ",")
            return normalized.split(",")
                .map { it.trim().removeSuffix(".") }
                .filter { it.isNotBlank() }
                .map { classify(it) }
                .distinct()
        }
    }
}
