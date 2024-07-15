package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PrimaryDetails(
    val Experience: String,
    val Fees_Charged: String,
    val Job_Type: String,
    val Place: String,
    val Qualification: String,
    val Salary: String
):Parcelable