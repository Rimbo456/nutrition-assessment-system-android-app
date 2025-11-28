package com.example.nutrition_assessment_system_android_app.ui.common.component.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Share
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton

@Composable
fun InternalNavigationBar(
    leftIcon: ImageVector,
    leftIconInRightZone: ImageVector,
    rightIconInRightZone: ImageVector,
    onLeftButtonClick: () -> Unit,
    onLeftButtonInRightZoneClick: () -> Unit,
    onRightButtonInRightZoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sizeIcon = 24.dp

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomIconButton(
                icon = leftIcon,
                onClick = onLeftButtonClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                iconColors = MaterialTheme.colorScheme.onSurface,
                sizeIcon = sizeIcon,
                modifier = Modifier
            )

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
                modifier = Modifier.padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomIconButton(
                        icon = leftIconInRightZone,
                        onClick = onLeftButtonInRightZoneClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        iconColors = MaterialTheme.colorScheme.onPrimaryContainer,
                        sizeIcon = sizeIcon,
                        modifier = Modifier
                    )
                    CustomIconButton(
                        icon = rightIconInRightZone,
                        onClick = onRightButtonInRightZoneClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        iconColors = MaterialTheme.colorScheme.onSecondaryContainer,
                        sizeIcon = sizeIcon,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InternalNavigationBarPreview() {
    MaterialTheme {
        InternalNavigationBar(
            leftIcon = Lucide.ArrowLeft,
            leftIconInRightZone = Lucide.Heart,
            rightIconInRightZone = Lucide.Share,
            onLeftButtonClick = { },
            onLeftButtonInRightZoneClick = { },
            onRightButtonInRightZoneClick = { }
        )
    }
}