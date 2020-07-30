package com.lisandrolopez.androiddevelopertest.ui.newrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.base.BaseFragment
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.util.Status

class NewRecordFragment : BaseFragment() {

    private var newRecordViewModel: NewRecordViewModel? = null
    private var btnSaveRecord: Button? = null
    private var etVehicleId: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_record, container, false)
        btnSaveRecord = view.findViewById(R.id.btn_save_vehicle_entry)
        etVehicleId = view.findViewById(R.id.et_car_id)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSaveRecord?.setOnClickListener {
            saveEntry()
        }
    }

    private fun initViewModel() {
        context?.let {
            val recordRepository = RecordRepository(AppDatabase.getDatabase(it))
            val factory = NewRecordViewModel.Factory(requireActivity().application, recordRepository)
            newRecordViewModel = ViewModelProvider(this, factory).get(NewRecordViewModel::class.java)
        }
    }

    private fun saveEntry() {
        hideKeyboard(btnSaveRecord)
        val vehicleId = etVehicleId?.text?.toString()
        newRecordViewModel?.saveEntry(vehicleId)?.observe(viewLifecycleOwner, Observer { result ->
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
}
