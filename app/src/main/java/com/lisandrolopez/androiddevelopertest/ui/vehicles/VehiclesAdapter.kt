package com.lisandrolopez.androiddevelopertest.ui.vehicles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithType

class VehiclesAdapter(private var vehicles: List<VehicleWithType>):
    RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = vehicles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.tvVehicle.text = vehicle.vehicle.registrationId
        holder.tvType.text = vehicle.vehicleType?.type
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvVehicle: TextView = itemView.findViewById(R.id.tv_vehicle_id)
        val tvType: TextView = itemView.findViewById(R.id.tv_official_vehicle)
    }
}
