package com.drowsynomad.pettersonweatherapp.utils

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.load
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.Constants

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

fun View.visibility(isVisible: Boolean) = this.apply {
    if (isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
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

fun AppCompatImageView.loadImage(url: String, imageLoader: ImageLoader) {
    this.load("${Constants.BASE_IMAGE_URL}${url}@2x.png", imageLoader) {
        crossfade(true)
    }
}

fun String.clearTemperature(): String =
    if(this.last() == '°') this.dropLast(1)
    else this

fun String.formatTemperature() = "$this°"

fun String.round() = substringBefore(".")