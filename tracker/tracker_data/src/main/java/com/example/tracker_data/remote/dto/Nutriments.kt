package com.example.tracker_data.remote.dto

import com.squareup.moshi.Json

data class Nutriments(

    @field:Json(name = "carbohydrates_100g")
    val carbohydrates100g: Double,


    @field:Json(name = "energy-kcal_100g")
    val energyKcal100g: Int,


    @field:Json(name = "fat_100g")
    val fat100g: Int,


    @field:Json(name = "proteins_100g")
    val proteins100g: Int,

)