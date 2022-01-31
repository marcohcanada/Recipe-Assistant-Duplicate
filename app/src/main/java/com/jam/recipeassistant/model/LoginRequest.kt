package com.jam.recipeassistant.model

/**
 * Created by 991470628 : MARCO HIDALGO ROMERO
 * on 2022-01-30
 */

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract

data class LoginRequest(
    var Email : String?,
    var Password : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Email)
        parcel.writeString(Password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginRequest> {
        override fun createFromParcel(parcel: Parcel): LoginRequest {
            return LoginRequest(parcel)
        }

        override fun newArray(size: Int): Array<LoginRequest?> {
            return arrayOfNulls(size)
        }
    }
}