package com.hoangnv97.moviedemo.presentation.main.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hoangnv97.moviedemo.R

class LoadingProgress : DialogFragment() {

    private class CustomProgressDialog(context: Context, theme: Int) : AlertDialog(context, theme) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.progress_dialog)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return CustomProgressDialog(requireContext(), R.style.ProgressDialog)
    }

    fun showLoadingOnActivity(activity: AppCompatActivity?) {
        activity?.run {
            showLoadingProgress(supportFragmentManager)
        }
    }

    fun showLoadingOnFragment(fragment: Fragment?) {
        fragment?.run {
            showLoadingProgress(childFragmentManager)
        }
    }

    private fun showLoadingProgress(fragmentManager: FragmentManager) {
        if (isAdded) return
        show(fragmentManager, this::class.java.name)
        fragmentManager.executePendingTransactions()
    }

    fun hideLoadingProgress() {
        if (isAdded) dismissAllowingStateLoss()
    }
}
