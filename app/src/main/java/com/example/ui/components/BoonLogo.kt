package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

/**
 * Official Brand Logo of Boon - Offline Quests & Growth.
 */
@Composable
fun BoonLogo(
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    elevation: Dp = 4.dp,
    shapeRadius: Dp = 12.dp
) {
    Image(
        painter = painterResource(id = R.drawable.ic_boon_logo),
        contentDescription = "Boon Logo",
        modifier = modifier
            .size(size)
            .then(if (elevation > 0.dp) Modifier.shadow(elevation, RoundedCornerShape(shapeRadius)) else Modifier)
            .clip(RoundedCornerShape(shapeRadius))
    )
}
