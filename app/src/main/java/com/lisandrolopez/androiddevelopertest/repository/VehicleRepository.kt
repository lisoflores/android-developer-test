package com.lisandrolopez.androiddevelopertest.repository

import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithType
import com.lisandrolopez.androiddevelopertest.repository.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VehicleRepository (private val db: AppDatabase) {

    suspend fun saveVehicle(vehicleId: String, type: VehicleType): Resource<Vehicle> {
        val vehicle = Vehicle(registrationId = vehicleId,
            vehicleType = type.id ?: 0,
            createdAtMillis = System.currentTimeMillis())
        db.vehicleDao().createVehicle(vehicle)
        return Resource.success(vehicle)
    }

    suspend fun getVehicleById(vehicleId: String): VehicleWithType? = withContext(Dispatchers.IO) {
        db.vehicleDao().getVehicleById(vehicleId)
    }

    fun getAllVehicles() = db.vehicleDao().getAllVehicles()

    fun getVehicleTypes() = db.vehicleType().getVehicleTypes()
}
