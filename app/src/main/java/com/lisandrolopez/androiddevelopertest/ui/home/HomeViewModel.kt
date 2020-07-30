package com.lisandrolopez.androiddevelopertest.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record
import com.lisandrolopez.androiddevelopertest.util.PaymentUtil
import com.lisandrolopez.androiddevelopertest.util.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(app: Application,
                    private val recordRepo: RecordRepository,
                    private val vehicleRepository: VehicleRepository) : AndroidViewModel(app) {

    val showPaymentDialogEvent = SingleLiveEvent<PaymentMessage>()

    fun getActiveRecords() = recordRepo.getActiveRecords()

    fun saveLeave(record: Record) {
        val leaveTime = System.currentTimeMillis()
        val stayTime = leaveTime - record.entryTime
        record.leaveTime = leaveTime
        record.stayTime = stayTime
        viewModelScope.launch {
            val vehicle = vehicleRepository.getVehicleById(record.vehicleId)
            val recordUpdated = recordRepo.saveLeave(record)

            val message = PaymentMessage()
            val app = getApplication<Application>()
            if (vehicle != null) {
                if (vehicle.vehicleType?.type == "Oficial") {
                    message.title = app.getString(R.string.payment_no_required)
                    message.message = app.getString(R.string.payment_no_required_is_official)
                } else {
                    message.title = app.getString(R.string.payment_no_required_at_this_time)
                    message.message = app.getString(R.string.payment_no_required_is_resident)
                }
            } else {
                val charge = PaymentUtil().calculatePayment(recordUpdated.stayTime ?: 0L, null)
                message.title = app.getString(R.string.charge_with_value, charge.charge.toString())
                message.message = app.getString(R.string.time_stay, charge.minutes.toString())
            }

            showPaymentDialogEvent.value = message
        }
    }

    class Factory(private val app: Application,
                  private val recordRepo: RecordRepository,
                  private val vehicleRepository: VehicleRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(app, recordRepo, vehicleRepository) as T
        }
    }
}