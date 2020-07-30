package com.lisandrolopez.androiddevelopertest.ui.newrecord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record
import com.lisandrolopez.androiddevelopertest.repository.util.Resource

class NewRecordViewModel(app: Application,
                         private val recordRepository: RecordRepository) : AndroidViewModel(app) {

    fun saveEntry(vehicleId: String?) = liveData<Resource<Record>> {
        val result = if (vehicleId.isNullOrEmpty()) {
            Resource.error(getApplication<Application>().getString(R.string.empty_vehicle_id_error))
        } else {
            recordRepository.saveEntry(vehicleId)
        }
        emit(result)
    }

    class Factory(
        private val app: Application,
        private val recordRepository: RecordRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewRecordViewModel(app, recordRepository) as T
        }

    }
}