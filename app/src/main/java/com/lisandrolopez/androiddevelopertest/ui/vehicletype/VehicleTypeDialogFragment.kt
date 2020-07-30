package com.lisandrolopez.androiddevelopertest.ui.vehicletype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleType
import com.lisandrolopez.androiddevelopertest.ui.addvehicle.CreateVehicleViewModel

class VehicleTypeDialogFragment : DialogFragment(), VehicleTypeAdapter.OnTypeSelected {

    private var rvVehicleType: RecyclerView? = null
    private var createVehicleViewModel: CreateVehicleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createVehicleViewModel = ViewModelProvider(requireActivity()).get(CreateVehicleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_select_vehicle_type, container, false)
        rvVehicleType = view.findViewById(R.id.rv_vehicle_type)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        createVehicleViewModel?.getVehicleTypes()?.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun initAdapter() {
        rvVehicleType?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvVehicleType?.layoutManager = layoutManager
    }

    private fun setData(types: List<VehicleType>) {
        val adapter = VehicleTypeAdapter(types, this)
        rvVehicleType?.adapter = adapter
    }

    companion object {
        fun getInstance() = VehicleTypeDialogFragment()
    }

    override fun onTypeSelected(type: VehicleType) {
        createVehicleViewModel?.vehicleType = type
        createVehicleViewModel?.typeSelected?.postValue(type)
        this.dismiss()
    }

}
