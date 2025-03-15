package org.homesharing.lockbox.domain.model.box

data class Box(
    val address: String,
    val alias: String,
    val batteryLevel: Double,
    val macAddress: String,
    val opened: Boolean,
    val signalStrength: Double,
)