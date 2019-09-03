package com.yourgains.mvpmoxydaggertemplate.data.entity.presentation

import android.os.Parcel
import android.os.Parcelable

data class NetworkErrorUiModel(
    val code: Int,
    val message: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetworkErrorUiModel> {
        override fun createFromParcel(parcel: Parcel): NetworkErrorUiModel {
            return NetworkErrorUiModel(parcel)
        }

        override fun newArray(size: Int): Array<NetworkErrorUiModel?> {
            return arrayOfNulls(size)
        }
    }

}