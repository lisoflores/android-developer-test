package com.lisandrolopez.androiddevelopertest.ui.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.base.BaseFragment
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Vehicle
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithType

class VehiclesFragment : BaseFragment(), View.OnClickListener {

    private var rvVehicles: RecyclerView? = null
    private var btnAddVehicle: Button? = null
    private var vehiclesViewModel: VehiclesViewModel? = null
    private var vehiclesAdapter: VehiclesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicles, container, false)
        rvVehicles = view.findViewById(R.id.rv_vehicles)
        btnAddVehicle = view.findViewById(R.id.btn_add_vehicle)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        btnAddVehicle?.setOnClickListener(this)
        vehiclesViewModel?.getAllVehicles()?.observe(viewLifecycleOwner, Observer { vehicles ->
            setData(vehicles)
        })
    }

    private fun initViewModel() {
        context?.let {
            val vehicleRepository = VehicleRepository(
                AppDatabase.getDatabase(it)
            )

            val factory = VehiclesViewModel.Factory(
                requireActivity().application,
                vehicleRepository
            )

            vehiclesViewModel = ViewModelProvider(this, factory).get(VehiclesViewModel::class.java)
        }
    }

    private fun initAdapter() {
        rvVehicles?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvVehicles?.layoutManager = layoutManager
    }

    private fun setData(vehicles: List<VehicleWithType>) {
        vehiclesAdapter = VehiclesAdapter(vehicles.toMutableList())
        rvVehicles?.adapter = vehiclesAdapter

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_vehicle -> {
                //open screen
                Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_createVehicleFragment)
            }
        }
    }
}