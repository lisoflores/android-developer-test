package com.lisandrolopez.androiddevelopertest.base

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun hideKeyboard(view: View?) {
        view?.let {
            val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun showDialog(title: String? = null, message: String, shouldBack: Boolean) {
        context?.let {
            val dialog = AlertDialog.Builder(it)
                .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    if (shouldBack) {
                        activity?.onBackPressed()
                    } else {
                        dialog.dismiss()
                    }
                }
                .create()

            title?.let { dialog.setTitle(title) }
            dialog.setMessage(message)
            dialog.setCancelable(false)
            dialog.show()
        }
    }
}