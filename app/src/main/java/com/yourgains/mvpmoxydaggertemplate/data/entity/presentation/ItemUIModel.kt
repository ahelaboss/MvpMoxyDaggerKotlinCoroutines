package com.yourgains.mvpmoxydaggertemplate.data.entity.presentation

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
data class ItemUIModel(val id: Int, val name: String) : Parcelable {

    constructor(parcel: Parcel) : this(id = parcel.readInt(), name = parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemUIModel> {
        override fun createFromParcel(parcel: Parcel): ItemUIModel {
            return ItemUIModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemUIModel?> {
            return arrayOfNulls(size)
        }
    }
}