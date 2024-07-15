package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Creative(
    val creative_type: Int,
    val `file`: String,
    val image_url: String,
    val order_id: Int,
    val thumb_url: String
):Parcelable