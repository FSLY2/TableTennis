package com.example.tabletennis.models

sealed class Players(val name: String, var score: Int = 0) {
    data class First(val pName: String): Players(pName)
    data class Second(val pName: String): Players(pName)
}