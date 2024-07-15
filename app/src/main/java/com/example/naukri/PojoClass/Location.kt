package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Location(
    val id: Int,
    val locale: String,
    val state: Int
):Parcelable