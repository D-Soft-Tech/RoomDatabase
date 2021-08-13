package com.example.roomdatabase.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class RoomTypeConverters {

    @TypeConverter
    fun fromBitMap(bitmap: Bitmap): ByteArray = run {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            outputStream
        )
        outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArray(byteArray: ByteArray): Bitmap = run {
        BitmapFactory.decodeByteArray(
            byteArray,
            0,
            byteArray.size
        )
    }
}
