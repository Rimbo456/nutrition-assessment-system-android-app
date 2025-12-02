package com.example.nutrition_assessment_system_android_app.ui.feature.overview.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

fun formatDateForUi(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    val today = LocalDate.now()

    val day = date.dayOfMonth
    val month = date.monthValue

    // Format ngày + tháng (ví dụ: "2 Th12")
    val dayMonthText = "$day Th$month"

    // Tính ngày so với hôm nay
    val daysDiff = java.time.Period.between(date, today).days

    return when (daysDiff) {
        0 -> "Hôm nay, $dayMonthText"
        1 -> "Hôm qua, $dayMonthText"
        -1 -> "Ngày mai, $dayMonthText"
        else -> {
            val dayOfWeek = when (date.dayOfWeek.value) {
                1 -> "Thứ Hai"
                2 -> "Thứ Ba"
                3 -> "Thứ Tư"
                4 -> "Thứ Năm"
                5 -> "Thứ Sáu"
                6 -> "Thứ Bảy"
                7 -> "Chủ Nhật"
                else -> ""
            }
            "$dayOfWeek, $dayMonthText"
        }
    }
}
