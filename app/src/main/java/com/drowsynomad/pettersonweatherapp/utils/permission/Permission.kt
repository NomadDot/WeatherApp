package com.drowsynomad.pettersonweatherapp.utils.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.drowsynomad.pettersonweatherapp.R

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

class Permission {
    companion object {
        fun ensureFineLocationPermission(
            context: AppCompatActivity,
            launcher: ActivityResultLauncher<String>,
            onPermissionGranted: () -> Unit,
            onPermissionDenied: () -> Unit,
        ) {
            fun askPermission() = launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> onPermissionGranted()

                ActivityCompat.shouldShowRequestPermissionRationale(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    val dialog = AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.location_permission_dialog_title))
                        .setMessage(context.getString(R.string.location_permission_dialog_description))
                        .setCancelable(false)
                        .setPositiveButton(context.getString(R.string.dialog_button_ok)) { _, _ ->
                            askPermission()
                        }
                        .setNegativeButton(context.getString(R.string.dialog_button_cancel)) { _, _ ->
                           onPermissionDenied()
                        }.show()
                }

                else -> askPermission()
            }
        }
    }
}