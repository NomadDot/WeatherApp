package com.drowsynomad.pettersonweatherapp.utils

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment


/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

fun View.visibility(isVisible: Boolean) = this.apply {
    if (isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun AppCompatEditText.onDone(action: (String) -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE)
            action.invoke(this.text.toString())
        true
    }
}

fun Fragment.openGpsSettings() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivity(intent)
}

fun Fragment.openPermissionSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", this.requireContext().packageName, null)
    )
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}


