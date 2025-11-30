package com.example.nutrition_assessment_system_android_app.ui.common.component.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.LucideIcon
import com.example.nutrition_assessment_system_android_app.R
import com.example.nutrition_assessment_system_android_app.ui.common.component.button.CustomIconButton
import com.google.accompanist.pager.PagerState

@Composable
fun BottomTabBar(
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
    navigateToCamera: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                ambientColor = Color.Black.copy(alpha = 0.08f),
                spotColor = Color.Black.copy(alpha = 0.12f)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomTabBarItem(
                id = "layout-grid",
                contentDescription = "Overview",
                isSelected = pagerState.currentPage == 0,
                onClick = {
                    onTabSelected(0)
                }
            )

            BottomTabBarItem(
                id = "chart-spline",
                contentDescription = "Statistics",
                isSelected = pagerState.currentPage == 1,
                onClick = {
                    onTabSelected(1)
                }
            )

            // Center Camera Button with elevated design
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    )
            ) {
                CustomIconButton(
                    icon = Lucide.Camera,
                    onClick = { navigateToCamera() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    iconColors = MaterialTheme.colorScheme.onPrimary,
                    sizeIcon = 28.dp,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }

            BottomTabBarItem(
                id = "message-circle",
                contentDescription = "Messages",
                isSelected = pagerState.currentPage == 2,
                onClick = {
                    onTabSelected(2)
                }
            )

            // Profile Avatar with refined styling
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        onTabSelected(3)
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun BottomTabBarItem(
    id: String,
    contentDescription: String?,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val sizeIcon = 24.dp

    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                else
                    Color.Transparent
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        LucideIcon(
            id = id,
            contentDescription = contentDescription,
            tint = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.size(sizeIcon)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomTabBarPreview() {
//    BottomTabBar()
}