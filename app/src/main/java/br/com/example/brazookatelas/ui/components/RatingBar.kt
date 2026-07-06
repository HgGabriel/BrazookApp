package br.com.example.brazookatelas.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.example.brazookatelas.ui.theme.StarGold
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = StarGold,
    starSize: Dp = 16.dp,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.size(starSize)
            )
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.size(starSize)
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Rounded.StarOutline,
                contentDescription = null,
                tint = starsColor.copy(alpha = 0.4f),
                modifier = Modifier.size(starSize)
            )
        }
    }
}
