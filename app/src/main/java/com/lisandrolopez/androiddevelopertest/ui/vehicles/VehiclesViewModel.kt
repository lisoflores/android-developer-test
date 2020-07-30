package com.lisandrolopez.androiddevelopertest.ui.vehicles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository

class VehiclesViewModel (app: Application,
                         private val vehicleRepository: VehicleRepository) : AndroidViewModel(app) {

    fun getAllVehicles() = vehicleRepository.getAllVehicles()

    class Factory(private val app: Application,
                  private val vehicleRepository: VehicleRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return VehiclesViewModel(app, vehicleRepository) as T
        }

    }
}
