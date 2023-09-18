package com.lightspark.sdk.remotesigning

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SigningJob(
    val id: String,
    @SerialName("derivation_path")
    val derivationPath: String,
    val message: String,
    @SerialName("add_tweak")
    val addTweak: String?,
    @SerialName("mul_tweak")
    val mulTweak: String?,
    @SerialName("is_raw")
    val isRaw: Boolean,
)
