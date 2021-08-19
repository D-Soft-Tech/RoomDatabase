package com.example.roomdatabase.data

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.roomdatabase.R
import pub.devrel.easypermissions.EasyPermissions

object AppConstants {

    val REQUEST_CODE_FOR_EXTERNAL_STORAGE = 111

    fun getImageLoadDefinition(imageView: ImageView, imageBitmap: Bitmap) {

        imageView.load(imageBitmap) {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.image_load_placeholder)
            transformations(CircleCropTransformation())
        }
    }

    fun checkForPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    fun requestForPermission(host: Fragment, requestCode: Int, permissionRationale: String) {
        EasyPermissions.requestPermissions(
            host,
            permissionRationale,
            requestCode,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}
