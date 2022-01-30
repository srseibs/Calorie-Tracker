package com.example.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity(
    val name: String,
    val amount: Int,

    val carbs: Int,
    val protein: Int,
    val fat: Int,

    val imageUrl: String?,
    val type: String,

    val dayOfMonth: Int,
    val month: Int,
    val year: Int,

    @PrimaryKey val id: Int? = null

)