package com.lisandrolopez.androiddevelopertest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.base.BaseFragment
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.model.Record

class HomeFragment : BaseFragment(), View.OnClickListener, ActiveRecordsAdapter.OnRecordLeaveClick {

    private var homeViewModel: HomeViewModel? = null
    private var btnRecordEntry: Button? = null
    private var rvActiveRecords: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        btnRecordEntry = view.findViewById(R.id.btn_add_entry)
        rvActiveRecords = view.findViewById(R.id.rv_active_records)
        btnRecordEntry?.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        homeViewModel?.getActiveRecords()?.observe(viewLifecycleOwner, Observer { result ->
            setActiveRecord(result)
        })

        homeViewModel?.showPaymentDialogEvent?.observe(viewLifecycleOwner, Observer { msg ->
            showDialog(msg.title, msg.message ?: "", false)
        })

    }

    private fun initViewModel() {
        context?.let {
            val recordRepository = RecordRepository(AppDatabase.getDatabase(it))
            val vehicleRepository = VehicleRepository(AppDatabase.getDatabase(it))
            val vmFactory = HomeViewModel.Factory(
                requireActivity().application,
                recordRepository,
                vehicleRepository)
            homeViewModel = ViewModelProvider(this, vmFactory).get(HomeViewModel::class.java)
        }
    }

    private fun initAdapter() {
        rvActiveRecords?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvActiveRecords?.layoutManager = layoutManager
    }

    private fun setActiveRecord(activeRecords: List<Record>) {
        val adapter = ActiveRecordsAdapter(activeRecords, this)
        rvActiveRecords?.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add_entry -> {
                //open screen
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_newRecordFragment)
            }
        }
    }

    override fun onRecordLeaveClick(record: Record) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(record.vehicleId)
                .setMessage(R.string.record_leave_confirmation)
                .setNegativeButton(getString(android.R.string.cancel)) { view, _ ->
                    view.dismiss()
                }
                .setPositiveButton(R.string.record_leave) { df, _ ->
                    df.dismiss()
                    homeViewModel?.saveLeave(record)
                }
                .create().show()
        }
    }
}
