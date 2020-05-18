package com.mygate.airtelx.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("requestId") val requestId: String?,
    @SerializedName("data") val data: Data?
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("addressList") val addressList: List<AddressList>?
) : Parcelable

@Parcelize
data class AddressList(
    @SerializedName("id") val id: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("addressString") val addressString: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("label") val label: String?
) : Parcelable