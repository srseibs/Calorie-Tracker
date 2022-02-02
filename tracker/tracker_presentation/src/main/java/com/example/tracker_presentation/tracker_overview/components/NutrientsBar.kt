package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.DietConstants
import com.plcoding.calorytracker.ui.theme.CarbColor
import com.plcoding.calorytracker.ui.theme.FatColor
import com.plcoding.calorytracker.ui.theme.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colors.background
    val caloriesExceededColor = MaterialTheme.colors.error
    val carbWidthRatio = remember { Animatable(initialValue = 0f) }
    val fatWidthRatio = remember { Animatable(initialValue = 0f) }
    val proteinWidthRatio = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(
            targetValue = ((carbs * DietConstants.CAL_PER_CARB_G) / calorieGoal)
        )
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(
            targetValue = ((protein * DietConstants.CAL_PER_PROTEIN_G) / calorieGoal)
        )
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(
            targetValue = ((fat * DietConstants.CAL_PER_FAT_G) / calorieGoal)
        )
    }

    Canvas(modifier = modifier) {
        val radius = CornerRadius(100f)

        if (calories <= calorieGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = radius
            )
            drawRoundRect(
                color = FatColor,
                size = Size(
                    width = carbsWidth +  proteinWidth + fatWidth,
                    height = size.height
                ),
                cornerRadius = radius
            )
            drawRoundRect(
                color = ProteinColor,
                size = Size(
                    width = carbsWidth +  proteinWidth,
                    height = size.height
                ),
                cornerRadius = radius
            )
            drawRoundRect(
                color = CarbColor,
                size = Size(
                    width = carbsWidth,
                    height = size.height
                ),
                cornerRadius = radius
            )
        } else {
            drawRoundRect(
                color = caloriesExceededColor,
                size = size,
                cornerRadius = radius
            )
        }
    }
}

@Preview
@Composable
fun NutrientBarPreview() {
    NutrientsBar(carbs = 94, protein = 54, fat = 19, calories = 400, calorieGoal = 2703,
        modifier = Modifier.size(width = 300.dp, height = 20.dp)
    )
}