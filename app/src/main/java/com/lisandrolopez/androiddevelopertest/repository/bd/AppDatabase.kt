package com.lisandrolopez.androiddevelopertest.repository.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.dao.RecordDao
import com.lisandrolopez.androiddevelopertest.repository.bd.dao.VehicleDao
import com.lisandrolopez.androiddevelopertest.repository.bd.dao.VehicleRecordDao
import com.lisandrolopez.androiddevelopertest.repository.bd.dao.VehicleTypeDao
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [Vehicle::class, Record::class, VehicleType::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao
    abstract fun recordDao(): RecordDao
    abstract fun vehicleWithRecords(): VehicleRecordDao
    abstract fun vehicleType(): VehicleTypeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "trivial_database"

        fun getDatabase(context: Context) : AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME)
                    .addCallback(seedDatabaseCallback(context)).build()
                INSTANCE = instance
                return instance
            }
        }

        private fun seedDatabaseCallback(context: Context) : Callback {
            return object: Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        var typeDao = getDatabase(context).vehicleType()
                        GlobalScope.launch {
                            typeDao.insertAll(getPreVehicleTypes())
                        }
                    }
                }
            }
        }

        private fun getPreVehicleTypes(): List<VehicleType> {
            val typesMap = HashMap<String, Double>().apply {
                put("Residente", 0.05)
                put("Oficial", 0.50)
            }

            val types = mutableListOf<VehicleType>()

            typesMap.keys.forEach {
                val type = VehicleType(
                    type = it,
                    price = typesMap[it] ?: 0.0
                )
                types.add(type)
            }

            return types
        }
    }
}
