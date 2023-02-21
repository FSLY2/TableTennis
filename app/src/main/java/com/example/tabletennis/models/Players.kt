package com.example.tabletennis.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Players : Parcelable {
    abstract var pName: String
    abstract var score: Int
    abstract var photo: String?

    @Parcelize
    data class First(
        override var pName: String = "",
        override var score: Int = 0,
        override var photo: String? = null
    ) : Players()

    @Parcelize
    data class Second(
        override var pName: String = "",
        override var score: Int = 0,
        override var photo: String? = null
    ) : Players()
}