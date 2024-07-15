package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class JobTag(
    val bg_color: String,
    val text_color: String,
    val value: String
):Parcelable