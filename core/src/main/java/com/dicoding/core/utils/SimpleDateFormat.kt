package com.dicoding.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}
