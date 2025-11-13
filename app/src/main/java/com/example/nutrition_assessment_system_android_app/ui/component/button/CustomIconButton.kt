package com.example.nutrition_assessment_system_android_app.ui.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.LucideIcon

@Composable
fun CustomIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    colors: IconButtonColors,
    iconColors: Color,
    sizeIcon: Dp,
    modifier: Modifier
) {
    IconButton(
        onClick = { onClick() },
        colors = colors,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            tint = iconColors,
            contentDescription = null,
            modifier = Modifier.size(sizeIcon)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomIconButtonPreview() {
    CustomIconButton(
        icon = Lucide.Camera,
        onClick = {},
        colors = IconButtonDefaults.iconButtonColors(Color.White),
        iconColors = Color.Black,
        sizeIcon = 36.dp,
        modifier = Modifier
    )
}