package com.example.nutrition_assessment_system_android_app.ui.common.component.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomTabBarItem(
            id = "layout-grid",
            contentDescription = null,
            isSelected = pagerState.currentPage == 0,
            onClick = {
                onTabSelected(0)
            }
        )
        BottomTabBarItem(
            id = "chart-spline",
            contentDescription = null,
            isSelected = pagerState.currentPage == 1,
            onClick = {
                onTabSelected(1)
            }
        )
        CustomIconButton(
            icon = Lucide.Camera,
            onClick = {  },
            colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary),
            iconColors = MaterialTheme.colorScheme.onPrimary,
            sizeIcon = 25.dp,
            modifier = Modifier.size(72.dp)
        )
        BottomTabBarItem(
            id = "message-circle",
            contentDescription = null,
            isSelected = pagerState.currentPage == 2,
            onClick = {
                onTabSelected(2)
            }
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(100))
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
        ) {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
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
    val sizeIcon = 25.dp
    Box(
        modifier = Modifier
            .size(60.dp)
            .clickable(
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
        LucideIcon(
            id = id,
            contentDescription = contentDescription,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(sizeIcon)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomTabBarPreview() {
//    BottomTabBar()
}