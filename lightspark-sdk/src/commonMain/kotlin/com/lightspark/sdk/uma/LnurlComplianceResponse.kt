package com.lightspark.sdk.uma

import kotlinx.serialization.Serializable

/**
 * The [compliance] field of the [LnurlpResponse].
 *
 * @property receiverKycStatus Indicates whether VASP2 has KYC information about the receiver.
 * @property isSubjectToTravelRule Indicates whether VASP2 is a financial institution that requires travel rule information.
 * @property receiverIdentifier The identifier of the receiver at VASP2.
 * @property signature The signature of the receiving VASP on the [signablePayload].
 * @property signatureNonce The nonce used in the signature.
 * @property signatureTimestamp The timestamp used in the signature.
 */
@Serializable
data class LnurlComplianceResponse(
    val receiverKycStatus: KycStatus,
    val isSubjectToTravelRule: Boolean,
    val receiverIdentifier: String,
    val signature: String,
    val signatureNonce: String,
    val signatureTimestamp: Long,
) {
    fun signablePayload() = "$receiverIdentifier|$signatureNonce|$signatureTimestamp".encodeToByteArray()

    fun signedWith(signature: String) = copy(signature = signature)
}
