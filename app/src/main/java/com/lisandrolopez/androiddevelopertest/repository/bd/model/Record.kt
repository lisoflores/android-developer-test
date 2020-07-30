package com.lisandrolopez.androiddevelopertest.repository.bd.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "record")
data class Record(
    @PrimaryKey val id: Int? = null,
    val vehicleId: String,
    val entryTime: Long,
    var leaveTime: Long? = null,
    var stayTime: Long? = null,
    var deleted: Boolean = false
) {

    fun getEntryDate() = getFormattedDate(entryTime)
    fun getLeaveDate() = getFormattedDate(leaveTime ?: 0L)
    fun getStayTimeInMinutes() {

    }

    private fun getFormattedDate(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val date = calendar.time
        return SimpleDateFormat("HH:mm:ss dd-MMM-yyyy", Locale.getDefault()).format(date)
    }
}
