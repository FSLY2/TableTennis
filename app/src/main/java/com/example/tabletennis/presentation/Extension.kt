package com.example.tabletennis.presentation

import android.text.Editable

fun Editable?.defIfEmptyOrNull(default: String): String =
    if (this == null || this.toString().isEmpty()) default
    else this.toString()