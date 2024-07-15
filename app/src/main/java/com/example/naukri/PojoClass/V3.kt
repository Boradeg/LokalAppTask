package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class V3(
    val field_key: String,
    val field_name: String,
    val field_value: String
):Parcelable