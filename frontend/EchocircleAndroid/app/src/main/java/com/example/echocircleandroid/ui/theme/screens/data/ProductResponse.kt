package com.example.echocircleandroid.ui.theme.screens.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ProductResponse(
    val product: Product,
    val httpStatus: String
)

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val company: String,
    val size: Int,
    val year: Int,
    val model: String,
    val serial: String,
    val image: String
) : Parcelable
