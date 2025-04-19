package com.dicoding.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String?): String {
    return try {
        if (dateString.isNullOrBlank() || dateString == "Unknown") return "-"
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = parser.parse(dateString)
        formatter.format(date ?: return "-")
    } catch (e: Exception) {
        "-"
    }
}

