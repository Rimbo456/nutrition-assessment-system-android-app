package com.example.nutrition_assessment_system_android_app.ui.common.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton

@Composable
fun InternalNavigationBar(
    leftIcon: ImageVector,
    leftIconInRightZone: ImageVector,
    rightIconInRightZone: ImageVector,
    onLeftButtonClick : () -> Unit,
    onLeftButtonInRightZoneClick : () ->Unit,
    onRightButtonInRightZoneClick : () ->Unit
) {
    val sizeIcon = 36.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CustomIconButton(
            icon = leftIcon,
            onClick = { onLeftButtonClick() },
            colors = IconButtonDefaults.iconButtonColors(Color.White),
            iconColors = Color.Black,
            sizeIcon = sizeIcon,
            modifier = Modifier
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomIconButton(
                icon = leftIconInRightZone,
                onClick = { onLeftButtonInRightZoneClick() },
                colors = IconButtonDefaults.iconButtonColors(Color.White),
                iconColors = Color.Black,
                sizeIcon = sizeIcon,
                modifier = Modifier
            )
            CustomIconButton(
                icon = rightIconInRightZone,
                onClick = { onRightButtonInRightZoneClick() },
                colors = IconButtonDefaults.iconButtonColors(Color.White),
                iconColors = Color.Black,
                sizeIcon = sizeIcon,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InternalNavigationBarPreview() {
}