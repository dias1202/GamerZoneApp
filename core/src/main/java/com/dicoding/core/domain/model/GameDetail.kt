package com.dicoding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameDetail(
    val id: Int,
    val name: String = "",
    val description: String = "",
    val released: String = "",
    val rating: Double = 0.0,
    val backgroundImage: String = "",
    val platforms: List<String?> = emptyList(),
    val isFavorite: Boolean = false
): Parcelable