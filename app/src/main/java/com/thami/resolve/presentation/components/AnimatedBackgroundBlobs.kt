package com.thami.resolve.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thami.resolve.presentation.theme.AquaGlow
import com.thami.resolve.presentation.theme.ElectricViolet

@Composable
fun AnimatedBackgroundBlobs(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "blobs")
    val offsetX1 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(tween(10000), RepeatMode.Reverse),
        label = "offsetX1"
    )
    val offsetY1 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(tween(9000), RepeatMode.Reverse),
        label = "offsetY1"
    )
    val scale1 = transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(8000), RepeatMode.Reverse),
        label = "scale1"
    )
    val offsetX2 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(tween(11000), RepeatMode.Reverse),
        label = "offsetX2"
    )
    val offsetY2 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 45f,
        animationSpec = infiniteRepeatable(tween(9500), RepeatMode.Reverse),
        label = "offsetY2"
    )
    val scale2 = transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(8500), RepeatMode.Reverse),
        label = "scale2"
    )
    val offsetX3 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(tween(10500), RepeatMode.Reverse),
        label = "offsetX3"
    )
    val offsetY3 = transition.animateFloat(
        initialValue = 0f,
        targetValue = 35f,
        animationSpec = infiniteRepeatable(tween(9000), RepeatMode.Reverse),
        label = "offsetY3"
    )
    val scale3 = transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(tween(9200), RepeatMode.Reverse),
        label = "scale3"
    )

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .offset(x = (-50 + offsetX1.value.toInt()).dp, y = (-50 + offsetY1.value.toInt()).dp)
                .size(160.dp)
                .scale(scale1.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ElectricViolet.copy(alpha = 0.4f),
                            ElectricViolet.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (200 - offsetX2.value.toInt()).dp, y = (260 - offsetY2.value.toInt()).dp)
                .size(200.dp)
                .scale(scale2.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AquaGlow.copy(alpha = 0.3f),
                            AquaGlow.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (70 + offsetX3.value.toInt()).dp, y = (100 + offsetY3.value.toInt()).dp)
                .size(140.dp)
                .scale(scale3.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ElectricViolet.copy(alpha = 0.25f),
                            ElectricViolet.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (-20 + offsetY1.value.toInt()).dp, y = (180 + offsetX1.value.toInt()).dp)
                .size(120.dp)
                .scale(scale2.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AquaGlow.copy(alpha = 0.28f),
                            AquaGlow.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (170 - offsetY2.value.toInt()).dp, y = (-30 + offsetX2.value.toInt()).dp)
                .size(170.dp)
                .scale(scale1.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ElectricViolet.copy(alpha = 0.32f),
                            ElectricViolet.copy(alpha = 0.14f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .offset(x = (40 + offsetX3.value.toInt()).dp, y = (290 - offsetY3.value.toInt()).dp)
                .size(130.dp)
                .scale(scale3.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AquaGlow.copy(alpha = 0.24f),
                            AquaGlow.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}