package com.thami.resolve.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.thami.resolve.presentation.theme.GlassBorder
import com.thami.resolve.presentation.theme.GlassSurface
import com.thami.resolve.presentation.theme.DeepIndigo

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 24,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    alpha: Float = 0.15f,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        GlassSurface.copy(alpha = alpha),
                        GlassSurface.copy(alpha = alpha * 0.5f)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(1.dp, GlassBorder.copy(alpha = 0.3f), RoundedCornerShape(cornerRadius))
            .padding(contentPadding)
    ) {
        content()
    }
}
