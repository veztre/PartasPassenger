package com.thesis.partas.passenger.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    var name: String,
    var email: String,
    var phone: String,
    var password: String,
): Parcelable {

}
