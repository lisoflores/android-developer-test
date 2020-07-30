package com.lisandrolopez.androiddevelopertest.repository.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Query("SELECT * FROM record WHERE deleted = 0 ORDER BY entryTime DESC")
    fun getActiveRecords(): LiveData<List<Record>>

    @Query("SELECT * FROM record WHERE deleted = 0")
    suspend fun getActiveEntries(): List<Record>

    @Update
    suspend fun clearEntries(entries: List<Record>)
}