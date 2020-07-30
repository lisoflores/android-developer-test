package com.lisandrolopez.androiddevelopertest.util

import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit

class PaymentUtil {
    fun calculatePayment(stayTimeInMillis: Long, vehicleType: VehicleType?) : ChargeInfo {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(stayTimeInMillis)
        val rate = vehicleType?.price ?: CHARGE_NO_RESIDENT_PER_MINUTE
        val charge = BigDecimal(minutes * rate).setScale(2, RoundingMode.HALF_UP).toDouble()
        return ChargeInfo(minutes = minutes.toInt(), charge = charge)
    }

    companion object {
        private const val CHARGE_NO_RESIDENT_PER_MINUTE = 0.50
    }

    data class ChargeInfo(
        var minutes: Int,
        var charge: Double
    )
}
