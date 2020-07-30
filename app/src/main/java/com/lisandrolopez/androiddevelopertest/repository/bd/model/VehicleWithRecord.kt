package com.lisandrolopez.androiddevelopertest.repository.bd.model

import androidx.room.Embedded
import androidx.room.Relation

data class VehicleWithRecord(
    @Embedded val vehicle: VehicleWithType,
    @Relation(
        entity = Record::class,
        parentColumn = "registrationId",
        entityColumn = "vehicleId"
    ) val records: List<Record>?
) {
    fun getTotalStayTime(): Long {
        var total = 0L
        records?.forEach {
            total += (it.stayTime ?: 0L)
        }

        return total
    }
}