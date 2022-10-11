package com.example.hairhood.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var dni: String,
    var usuario: String,
    var tipo: String

) : Parcelable