package com.lisandrolopez.androiddevelopertest.repository

import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record
import com.lisandrolopez.androiddevelopertest.repository.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecordRepository (private val db: AppDatabase) {

    suspend fun saveEntry(vehicleId: String) : Resource<Record> {
        val record = Record(
            vehicleId = vehicleId,
            entryTime = System.currentTimeMillis()
        )
        db.recordDao().createRecord(record)

        return Resource.success(record)
    }

    suspend fun saveLeave(record: Record) : Record {
        db.recordDao().updateRecord(record)
        return record
    }

    fun getActiveRecords() = db.recordDao().getActiveRecords()
    fun getVehiclesWithRecords() = db.vehicleWithRecords().getVehiclesWithRecords()

    suspend fun clearRecords() = withContext(Dispatchers.IO) {
        val activeRecords = db.recordDao().getActiveEntries()
        activeRecords.forEach {
            it.deleted = true
        }
        db.recordDao().clearEntries(activeRecords)
    }
}
