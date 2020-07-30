package com.lisandrolopez.androiddevelopertest.repository.bd.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_type")
data class VehicleType(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val type: String,
    val price: Double
)