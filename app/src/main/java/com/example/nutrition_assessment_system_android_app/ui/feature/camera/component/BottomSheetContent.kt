package com.example.nutrition_assessment_system_android_app.ui.feature.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.*
import com.example.nutrition_assessment_system_android_app.domain.model.Dish
import com.example.nutrition_assessment_system_android_app.domain.model.NutritionItem
import com.example.nutrition_assessment_system_android_app.domain.model.TotalNutrition
import com.example.nutrition_assessment_system_android_app.ui.feature.camera.enums.MealType
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    dish: Dish?,
    selectedMealType: MealType? = null,
    isEditing: Boolean = false,
    onEditToggle: () -> Unit = {},
    onWeightChange: (Double) -> Unit = {},
    onIngredientWeightChange: (String, Double) -> Unit = { _, _ -> },
    onIngredientRemove: (String) -> Unit = {},
    onIngredientAdd: () -> Unit = {},
    onMealTypeChange: (MealType) -> Unit = {},
    onSave: () -> Unit = {},
) {
    if (dish == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        // Fixed Header Section
        Column {
            // Header with edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dish.name.replace("_", " ").replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(
                    onClick = onEditToggle,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            if (isEditing) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant,
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isEditing) Lucide.Check else Lucide.Pencil,
                        contentDescription = if (isEditing) "Save" else "Edit",
                        tint = if (isEditing) MaterialTheme.colorScheme.onPrimary
                               else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Meal Type Selector
            MealTypeSelector(
                selectedType = selectedMealType,
                onTypeChange = onMealTypeChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nutrition overview - compact card
            NutritionOverviewCard(
                totalNutrition = dish.totalNutrition,
                totalWeight = dish.totalWeightG,
                isEditing = isEditing,
                onWeightChange = onWeightChange
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scrollable Ingredients Section (takes available space)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Ingredients section header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ingredients (${dish.ingredients.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (isEditing) {
                    TextButton(
                        onClick = onIngredientAdd
                    ) {
                        Icon(
                            imageVector = Lucide.Plus,
                            contentDescription = "Add ingredient",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ingredients list - scrollable
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dish.ingredients.toList()) { (ingredientName, weight) ->
                    val nutrition = dish.nutrition[ingredientName]
                    if (nutrition != null) {
                        MinimalIngredientItem(
                            name = ingredientName,
                            weight = weight,
                            nutrition = nutrition,
                            isEditing = isEditing,
                            onWeightChange = { newWeight ->
                                onIngredientWeightChange(ingredientName, newWeight)
                            },
                            onRemove = {
                                onIngredientRemove(ingredientName)
                            }
                        )
                    }
                }
            }
        }

        // Fixed Bottom Section - Save Button (only visible when not editing)
        if (!isEditing) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                // Save button - visible when not editing
                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Lucide.Save,
                        contentDescription = "Save",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun MealTypeSelector(
    selectedType: MealType?,
    onTypeChange: (MealType) -> Unit
) {
    val mealTypes = MealType.values().toList()

    // Map enum to display names
    val mealTypeLabels = mapOf(
        MealType.BREAKFAST to "Sáng",
        MealType.LUNCH to "Trưa",
        MealType.DINNER to "Tối",
        MealType.SNACK to "Ăn vặt"
    )

    Column {
        Text(
            text = "Meal Type",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            mealTypes.forEach { type ->
                FilterChip(
                    onClick = { onTypeChange(type) },
                    label = {
                        Text(
                            text = mealTypeLabels[type] ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    selected = selectedType == type,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NutritionOverviewCard(
    totalNutrition: TotalNutrition,
    totalWeight: Double,
    isEditing: Boolean,
    onWeightChange: (Double) -> Unit
) {
    var weightText by remember { mutableStateOf(totalWeight.roundToInt().toString()) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Weight and calories in one row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    if (isEditing) {
                        OutlinedTextField(
                            value = weightText,
                            onValueChange = { newText ->
                                weightText = newText
                                newText.toDoubleOrNull()?.let { onWeightChange(it) }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.width(70.dp),
                            textStyle = MaterialTheme.typography.titleLarge.copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            suffix = { Text("g", style = MaterialTheme.typography.titleLarge) }
                        )
                    } else {
                        Text(
                            text = "${totalWeight.roundToInt()}g",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "Weight",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${totalNutrition.calories.roundToInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B35)
                    )
                    Text(
                        text = "Cal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Macros in a compact row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CompactMacroItem(
                    label = "Protein",
                    value = totalNutrition.proteinG,
                    color = Color(0xFF4ECDC4)
                )

                CompactMacroItem(
                    label = "Fat",
                    value = totalNutrition.fatG,
                    color = Color(0xFFFFD93D)
                )

                CompactMacroItem(
                    label = "Carbs",
                    value = totalNutrition.carbsG,
                    color = Color(0xFF6BCF7F)
                )
            }
        }
    }
}

@Composable
private fun CompactMacroItem(
    label: String,
    value: Double,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            fontSize = 11.sp
        )
        Text(
            text = String.format(Locale.US, "%.1f", value),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
private fun MinimalIngredientItem(
    name: String,
    weight: Double,
    nutrition: NutritionItem,
    isEditing: Boolean,
    onWeightChange: (Double) -> Unit,
    onRemove: () -> Unit
) {
    var weightText by remember { mutableStateOf(weight.roundToInt().toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Name and nutrition summary
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name.replace("_", " ").replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${nutrition.calories.roundToInt()}cal • ${String.format(Locale.US, "%.1f", nutrition.proteinG)}pro • ${String.format(Locale.US, "%.1f", nutrition.fatG)}fat • ${String.format(Locale.US, "%.1f", nutrition.carbsG)}carb",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }

        // Right side - Weight and actions
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isEditing) {
                // Remove button
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Minus,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Editable weight
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { newText ->
                        weightText = newText
                        newText.toDoubleOrNull()?.let { onWeightChange(it) }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(60.dp),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    ),
                    suffix = { Text("g", fontSize = 12.sp) }
                )
            } else {
                // Display weight
                Text(
                    text = "${weight.roundToInt()}g",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
