package com.lisandrolopez.androiddevelopertest.repository.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithType

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createVehicle(vehicle: Vehicle)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle ORDER BY createdAtMillis DESC")
    fun getAllVehicles() : LiveData<List<VehicleWithType>>

    @Query("SELECT * FROM vehicle WHERE registrationId=:vehicleId ")
    fun getVehicleById(vehicleId: String) : VehicleWithType?

}
