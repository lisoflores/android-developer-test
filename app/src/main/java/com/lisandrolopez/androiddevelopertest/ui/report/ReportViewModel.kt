package com.lisandrolopez.androiddevelopertest.ui.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import kotlinx.coroutines.launch

class ReportViewModel (app: Application,
                       private val recordRepo: RecordRepository,
                       private val vehicleRepository: VehicleRepository
) : AndroidViewModel(app) {

    val getReportEvent = recordRepo.getVehiclesWithRecords()

    fun clearEntries() {
        viewModelScope.launch {
            recordRepo.clearRecords()
        }
    }

    class Factory(private val app: Application,
                  private val recordRepo: RecordRepository,
                  private val vehicleRepository: VehicleRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReportViewModel(app, recordRepo, vehicleRepository) as T
        }
    }
}