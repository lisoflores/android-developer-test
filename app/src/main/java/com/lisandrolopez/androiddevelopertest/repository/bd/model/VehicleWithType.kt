package com.lisandrolopez.androiddevelopertest.repository.bd.model

import androidx.room.Embedded
import androidx.room.Relation

data class VehicleWithType(
    @Embedded val vehicle: Vehicle,
    @Relation(
        entity = VehicleType::class,
        parentColumn = "vehicleType",
        entityColumn = "id"
    ) val vehicleType: VehicleType?
)