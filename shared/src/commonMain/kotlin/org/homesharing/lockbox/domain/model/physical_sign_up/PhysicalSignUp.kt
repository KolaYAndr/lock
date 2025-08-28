package org.homesharing.lockbox.domain.model.physical_sign_up

data class PhysicalSignUp(
    val macAddress: String,
    val ownerSub: String,
    val address: String,
    val alias: String,
)