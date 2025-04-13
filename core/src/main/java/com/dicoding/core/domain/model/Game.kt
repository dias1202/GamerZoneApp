package com.dicoding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String = "",
    val released: String = "",
    val rating: Double = 0.0,
    val backgroundImage: String = "",
    val platforms: String = "",
    val isFavorite: Boolean = false
): Parcelable