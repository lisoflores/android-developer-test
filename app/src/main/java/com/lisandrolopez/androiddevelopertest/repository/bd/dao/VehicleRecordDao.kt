package com.lisandrolopez.androiddevelopertest.repository.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithRecord

@Dao
interface VehicleRecordDao {

    @Transaction
    @Query("SELECT * FROM vehicle INNER JOIN record ON vehicle.registrationId = record.vehicleId WHERE record.deleted = 0")
    fun getVehiclesWithRecords(): LiveData<List<VehicleWithRecord>>

}
