package com.example.naukri.PojoClass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ContentV3(
    val V3: @RawValue List<V3>
):Parcelable