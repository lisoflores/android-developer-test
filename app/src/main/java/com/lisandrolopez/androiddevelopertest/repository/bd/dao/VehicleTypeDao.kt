package com.lisandrolopez.androiddevelopertest.repository.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType

@Dao
interface VehicleTypeDao {

    @Query("SELECT * FROM vehicle_type")
    fun getVehicleTypes(): LiveData<List<VehicleType>>

    @Insert
    suspend fun insertAll(types: List<VehicleType>)
}