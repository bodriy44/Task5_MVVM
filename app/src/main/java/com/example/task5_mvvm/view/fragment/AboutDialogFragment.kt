package com.example.task5_mvvm.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.view.AboutActivity

class AboutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.about_dialog_info)
                .setMessage(R.string.about_dialog_message)
                .setNegativeButton(R.string.about_dialog_neg_but, null)
                .setPositiveButton(R.string.about_dialog_pos_but) { dialog, _ ->
                    dialog.cancel()
                    startActivity(
                        Intent(
                            it,
                            AboutActivity::class.java
                        )
                    )
                }
                .create()
        } ?: throw IllegalStateException("Activity не может быть null")
    }
}