package org.homesharing.lockbox.domain.model.box

data class Box(
    val address: String,
    val alias: String,
    val batteryLevel: Double,
    val macAddress: String,
    val opened: Boolean,
    val signalStrength: Double,
) {
    companion object {
        const val BATTERY_PERCENT_LOW = 10
        const val BATTERY_PERCENT_MEDIUM = 40
        const val BATTERY_PERCENT_HIGH = 70

        const val SIGNAL_STRENGTH_LEVEL_LOW = -90
        const val SIGNAL_STRENGTH_LEVEL_MEDIUM = -60
        const val SIGNAL_STRENGTH_LEVEL_HIGH = -40
        const val SIGNAL_STRENGTH_LEVEL_FULL = -20
    }
}