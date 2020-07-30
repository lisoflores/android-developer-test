package com.lisandrolopez.androiddevelopertest.ui.addvehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.base.BaseFragment
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.util.Status
import com.lisandrolopez.androiddevelopertest.ui.vehicletype.VehicleTypeDialogFragment

class CreateVehicleFragment : BaseFragment() {

    private var etCarId: EditText? = null
    private var btnSave: Button? = null
    private var etVehicleType: EditText? = null
    private var createVehicleViewModel: CreateVehicleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_vehicle, container, false)
        etCarId = view.findViewById(R.id.et_car_id)
        btnSave = view.findViewById(R.id.btn_save_vehicle)
        etVehicleType = view.findViewById(R.id.et_vehicle_type)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSave?.setOnClickListener {
            saveVehicle()
        }

        etVehicleType?.setOnClickListener {
            openVehicleTypeDialog()
        }

        createVehicleViewModel?.typeSelected?.observe(viewLifecycleOwner, Observer {
            etVehicleType?.setText(it.type)
        })
    }

    private fun initViewModel() {
        context?.let {
            val vehicleRepository = VehicleRepository(
                AppDatabase.getDatabase(it)
            )

            val factory = CreateVehicleViewModel.Factory(
                requireActivity().application,
                vehicleRepository
            )

            createVehicleViewModel = ViewModelProvider(requireActivity(), factory).get(CreateVehicleViewModel::class.java)
        }
    }

    private fun saveVehicle() {
        hideKeyboard(btnSave)
        val vehicleId = etCarId?.text?.toString()
        createVehicleViewModel
            ?.saveVehicleEvent(vehicleId)
            ?.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    showDialog(message = getString(R.string.vehicule_saved_sucess), shouldBack = true)
                }
                Status.ERROR -> {
                    showDialog(message = result.message ?: "", shouldBack = false)
                }
            }
        })
    }

    private fun openVehicleTypeDialog() {
        val dialog = VehicleTypeDialogFragment.getInstance()
        dialog.show(childFragmentManager, "TYPE")
    }
}
