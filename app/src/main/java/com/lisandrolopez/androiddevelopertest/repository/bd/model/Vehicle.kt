package com.lisandrolopez.androiddevelopertest.repository.bd.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class Vehicle(
    @PrimaryKey val registrationId: String,
    val vehicleType: Int,
    val createdAtMillis: Long
)