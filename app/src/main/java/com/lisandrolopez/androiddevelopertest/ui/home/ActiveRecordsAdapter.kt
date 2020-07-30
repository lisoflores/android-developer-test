package com.lisandrolopez.androiddevelopertest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record

class ActiveRecordsAdapter(private val activeRecords: List<Record>,
                           private val callback: OnRecordLeaveClick): RecyclerView.Adapter<ActiveRecordsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_active_records, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = activeRecords.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = activeRecords[position]
        holder.tvVehicle.text = record.vehicleId
        holder.tvEntryTime.text = holder.tvEntryTime.context.getString(R.string.entry_with_time, record.getEntryDate())
        holder.tvLeaveTime.text = holder.tvLeaveTime.context.getString(R.string.leave_with_time, record.getLeaveDate())

        if (record.leaveTime != null) {
            holder.btnRecordLeave.visibility = View.GONE
            holder.tvLeaveTime.visibility = View.VISIBLE
        } else {
            holder.btnRecordLeave.visibility = View.VISIBLE
            holder.tvLeaveTime.visibility = View.GONE
            holder.btnRecordLeave.setOnClickListener {
                callback.onRecordLeaveClick(record)
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvVehicle: TextView = itemView.findViewById(R.id.tv_vehicle_id)
        val tvEntryTime: TextView = itemView.findViewById(R.id.tv_entry_time)
        val tvLeaveTime: TextView = itemView.findViewById(R.id.tv_leave_time)
        val btnRecordLeave: Button = itemView.findViewById(R.id.btn_record_leave)
    }

    interface OnRecordLeaveClick {
        fun onRecordLeaveClick(record: Record)
    }
}
