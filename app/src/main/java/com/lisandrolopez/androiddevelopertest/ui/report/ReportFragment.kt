package com.lisandrolopez.androiddevelopertest.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisandrolopez.androiddevelopertest.R
import com.lisandrolopez.androiddevelopertest.base.BaseFragment
import com.lisandrolopez.androiddevelopertest.repository.RecordRepository
import com.lisandrolopez.androiddevelopertest.repository.VehicleRepository
import com.lisandrolopez.androiddevelopertest.repository.bd.AppDatabase
import com.lisandrolopez.androiddevelopertest.repository.bd.model.VehicleWithRecord
import com.lisandrolopez.androiddevelopertest.util.PaymentUtil

class ReportFragment : BaseFragment() {

    private var reportViewModel: ReportViewModel? = null
    private var rvReport: RecyclerView? = null
    private var btnStartMonth: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        rvReport = view.findViewById(R.id.rv_report)
        btnStartMonth = view.findViewById(R.id.btn_start_month)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        reportViewModel?.getReportEvent?.observe(viewLifecycleOwner, Observer { result ->
            setReport(result)
        })

        btnStartMonth?.setOnClickListener {
            showStartMonthConfirmation()
        }
    }

    private fun initViewModel() {
        context?.let {
            val recordRepository = RecordRepository(AppDatabase.getDatabase(it))
            val vehicleRepository = VehicleRepository(AppDatabase.getDatabase(it))
            val vmFactory = ReportViewModel.Factory(
                requireActivity().application,
                recordRepository,
                vehicleRepository)
            reportViewModel = ViewModelProvider(this, vmFactory).get(ReportViewModel::class.java)
        }
    }

    private fun initAdapter() {
        rvReport?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvReport?.layoutManager = layoutManager
    }

    private fun setReport(vehicles: List<VehicleWithRecord>) {
        val adapter = ReportAdapter(vehicles, PaymentUtil())
        rvReport?.adapter = adapter
    }

    private fun showStartMonthConfirmation() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.start_month_confirmation_message)
                .setNegativeButton(getString(android.R.string.cancel)) { view, _ ->
                    view.dismiss()
                }
                .setPositiveButton(R.string.record_leave) { df, _ ->
                    df.dismiss()
                    reportViewModel?.clearEntries()
                }
                .create().show()
        }
    }
}
