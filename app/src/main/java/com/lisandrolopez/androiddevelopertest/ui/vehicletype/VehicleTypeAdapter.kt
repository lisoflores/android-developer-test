package com.lisandrolopez.androiddevelopertest.ui.vehicletype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType

class VehicleTypeAdapter(private val vehicleTypeList: List<VehicleType>,
                         private val callback: OnTypeSelected): RecyclerView.Adapter<VehicleTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle_type, parent, false)
        )
    }

    override fun getItemCount() = vehicleTypeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = vehicleTypeList[position]

        holder.tvType.text = type.type
        holder.tvType.setOnClickListener {
            callback.onTypeSelected(type)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvType = itemView as TextView
    }

    interface OnTypeSelected {
        fun onTypeSelected(type: VehicleType)
    }
}
