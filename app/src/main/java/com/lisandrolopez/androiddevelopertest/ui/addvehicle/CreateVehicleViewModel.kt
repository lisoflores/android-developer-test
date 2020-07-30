package com.lisandrolopez.androiddevelopertest.ui.addvehicle

import android.app.Application
import androidx.lifecycle.*
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType
import com.lisandrolopez.androiddevelopertest.repository.util.Resource

class CreateVehicleViewModel(app: Application,
                             private val vehicleRepository: VehicleRepository) : AndroidViewModel(app) {

    var vehicleType: VehicleType? = null
    var typeSelected = MutableLiveData<VehicleType>()

    fun saveVehicleEvent(vehicleId: String?) = liveData<Resource<Vehicle>> {
        val result = if (!vehicleId.isNullOrEmpty() && vehicleType != null) {
            vehicleRepository.saveVehicle(vehicleId, vehicleType!!)
        } else {
            Resource.error<Vehicle>(
                getApplication<Application>().getString(R.string.empty_vehicle_id_error)
            )
        }

        emit(result)
    }

    fun getVehicleTypes() = vehicleRepository.getVehicleTypes()

    class Factory(private val app: Application,
                  private val vehicleRepository: VehicleRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CreateVehicleViewModel(app, vehicleRepository) as T
        }

    }
}
