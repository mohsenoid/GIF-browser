package com.mohsenoid.gifbrowser.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class GifModel(
    val id: String,
    val title: String,
    val url: String,
    val thumbUrl: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString()!!,
        title = parcel.readString()!!,
        url = parcel.readString()!!,
        thumbUrl = parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(title)
        dest?.writeString(url)
        dest?.writeString(thumbUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GifModel> {
        override fun createFromParcel(parcel: Parcel): GifModel {
            return GifModel(parcel)
        }

        override fun newArray(size: Int): Array<GifModel?> {
            return arrayOfNulls(size)
        }
    }
}
