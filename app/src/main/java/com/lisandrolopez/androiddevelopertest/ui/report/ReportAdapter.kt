package com.lisandrolopez.androiddevelopertest.ui.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithRecord
import com.lisandrolopez.androiddevelopertest.util.PaymentUtil

class ReportAdapter(private val vehicleWithRecords: List<VehicleWithRecord>,
                    private val paymentUtil: PaymentUtil) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        }else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (viewType == TYPE_HEADER) {
            R.layout.item_report_header
        } else {
            R.layout.item_report
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = vehicleWithRecords.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != 0) {
            val relativePosition = position - 1
            val record = vehicleWithRecords[relativePosition]
            val payment = paymentUtil.calculatePayment(record.getTotalStayTime(), record.vehicle.vehicleType)
            val vehicleText = "${record.vehicle.vehicle?.registrationId} ${record.vehicle.vehicleType?.type}"
            holder.tvVehicle.text = vehicleText
            holder.tvTotalTime.text = payment.minutes.toString()
            holder.tvTotalCharged.text = payment.charge.toString()
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvVehicle: TextView = itemView.findViewById(R.id.tv_vehicle_id)
        val tvTotalTime: TextView = itemView.findViewById(R.id.tv_total_time)
        val tvTotalCharged: TextView = itemView.findViewById(R.id.tv_total_charged)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }
}