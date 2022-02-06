package com.example.tracker_data.repository

import com.example.core.domain.DietConstants
import com.example.tracker_data.local.TrackerDao
import com.example.tracker_data.mapper.toTrackableFood
import com.example.tracker_data.mapper.toTrackedFood
import com.example.tracker_data.mapper.toTrackedFoodEntity
import com.example.tracker_data.remote.OpenFoodApi
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories =
                            it.nutriments.carbohydrates100g * DietConstants.CAL_PER_CARB_G +
                                    it.nutriments.fat100g * DietConstants.CAL_PER_FAT_G +
                                    it.nutriments.proteins100g * DietConstants.CAL_PER_PROTEIN_G
                        val lowerBound = calculatedCalories * 0.99f
                        val upperBound = calculatedCalories * 1.02f
                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }
                    .map { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodForDate(
            month = localDate.monthValue,
            day = localDate.dayOfMonth,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}