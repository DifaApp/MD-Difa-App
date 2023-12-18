package com.difa.difaapp.data.local.entity

data class Recognition(val label: String, val confidence: Float) {
    val probabilityString = String.format("%.1f%%", confidence * 100.0f)
    override fun toString(): String {
        return "$label / $probabilityString"
    }
}