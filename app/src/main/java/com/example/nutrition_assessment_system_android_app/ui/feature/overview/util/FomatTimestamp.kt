package com.example.nutrition_assessment_system_android_app.ui.feature.overview.util

fun formatTimestampToTimeString(timestamp: String): String {
    // Assuming the timestamp is in ISO 8601 format: "YYYY-MM-DDTHH:MM:SSZ"
    return try {
        val timePart = timestamp.split("T")[1]
        val time = timePart.split("Z")[0]
        val hourMinute = time.split(":")
        "${hourMinute[0]}:${hourMinute[1]}"
    } catch (e: Exception) {
        "Invalid Timestamp"
    }
}