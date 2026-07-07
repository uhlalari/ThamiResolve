package com.thami.resolve.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AnimatedTotal(total: Double, modifier: Modifier = Modifier) {
    val animatedTotal by animateFloatAsState(
        targetValue = total.toFloat(),
        animationSpec = tween(durationMillis = 500),
        label = "animatedTotal"
    )
    val formatter = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }
    Text(
        text = formatter.format(animatedTotal.toDouble()),
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
}
