package com.example.nutrition_assessment_system_android_app.ui.common.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.*

@Composable
fun CustomIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconColors: Color = Color.Black,
    sizeIcon: Dp = 24.dp,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    contentDescription: String? = null,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors
    ) {
        Icon(
            imageVector = icon,
            tint = iconColors,
            contentDescription = contentDescription,
            modifier = Modifier.size(sizeIcon)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomIconButtonPreview() {
    CustomIconButton(
        icon = Lucide.Camera,
        onClick = {},
        iconColors = Color.Black,
        sizeIcon = 28.dp
    )
}