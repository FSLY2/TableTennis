package com.example.tabletennis.common

import android.text.Editable
import android.view.View

fun Editable?.defIfEmptyOrNull(default: String): String =
    if (this == null || this.toString().isEmpty()) default
    else this.toString()

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.changeVisibility(visible: Boolean?) {
    visibility = when (visible) {
        true -> View.VISIBLE
        false -> View.INVISIBLE
        null -> View.GONE
    }
}