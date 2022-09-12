package com.example.tabletennis.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Players : Parcelable {
    abstract var pName: String
    abstract var score: Int

    @Parcelize
    data class First(
        override var pName: String = "",
        override var score: Int = 0
    ) : Players()

    @Parcelize
    data class Second(
        override var pName: String = "",
        override var score: Int = 0
    ) : Players()
}