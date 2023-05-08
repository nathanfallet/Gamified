package me.nathanfallet.gamified.models

import android.os.Parcel
import android.os.Parcelable

data class Counter(
    /**
     * Icon shown in the counter
     */
    var icon: Int,

    /**
     * Text shown in the counter
     */
    var text: String,

    /**
     * Value of the counter
     */
    var count: Long
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(icon)
        parcel.writeString(text)
        parcel.writeLong(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Counter> {
        override fun createFromParcel(parcel: Parcel): Counter {
            return Counter(parcel)
        }

        override fun newArray(size: Int): Array<Counter?> {
            return arrayOfNulls(size)
        }
    }

}
