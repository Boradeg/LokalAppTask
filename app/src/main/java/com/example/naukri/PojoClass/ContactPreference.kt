package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ContactPreference(
    val preference: Int,
    val preferred_call_end_time: String,
    val preferred_call_start_time: String,
    val whatsapp_link: String
):Parcelable