package com.example.tracker_domain.use_case

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,
    ) {
        return repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g * amount) / 100f).roundToInt(),
                protein = ((food.proteinPer100g * amount) / 100f).roundToInt(),
                fat = ((food.fatPer100g * amount) / 100f).roundToInt(),
                calories = ((food.caloriesPer100g * amount) / 100f).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date
            )
        )
    }
}