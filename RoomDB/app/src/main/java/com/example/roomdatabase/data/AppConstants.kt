package com.example.roomdatabase.data

import android.graphics.Bitmap
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.roomdatabase.R

object AppConstants {

    fun getImageLoadDefinition(imageView: ImageView, imageBitmap: Bitmap) {

        imageView.load(imageBitmap) {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.image_load_placeholder)
            transformations(CircleCropTransformation())
        }
    }
}
