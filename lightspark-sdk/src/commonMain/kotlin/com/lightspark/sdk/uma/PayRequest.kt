package com.lightspark.sdk.uma

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The request sent by the sender to the receiver to retrieve an invoice.
 *
 * @property currencyCode The currency code that the receiver will receive for this payment.
 * @property amount The amount of the specified currency that the receiver will receive for this payment in the smallest
 *     unit of the specified currency (i.e. cents for USD).
 * @property payerData The data that the sender will send to the receiver to identify themselves.
 */
@Serializable
data class PayRequest(
    @SerialName("currency")
    val currencyCode: String,
    val amount: Long,
    val payerData: PayerData,
) {
    fun signablePayload() =
        payerData.compliance?.let {
            "${payerData.identifier}|${it.signatureNonce}|${it.signatureTimestamp}".encodeToByteArray()
        } ?: "${payerData.identifier}".encodeToByteArray()
}

@Serializable
data class PayerData @JvmOverloads constructor(
    val identifier: String,
    val name: String? = null,
    val email: String? = null,
    val compliance: CompliancePayerData? = null,
)

/**
 * The compliance data from the sender, including utxo info.
 *
 * @property utxos The list of UTXOs of the sender's channels that might be used to fund the payment.
 * @property nodePubKey If known, the public key of the sender's node. If supported by the receiver VASP's compliance
 *     provider, this will be used to pre-screen the sender's UTXOs for compliance purposes.
 * @property senderKycStatus Indicates whether VASP1 has KYC information about the sender.
 * @property travelRuleInfo The travel rule information of the sender. This is encrypted with the receiver's public
 *     encryption key.
 * @property utxoCallback The URL that the receiver will call to send UTXOs of the channel that the receiver used to
 *     receive the payment once it completes.
 * @property signature The signature of the sender on the signable payload.
 * @property signatureNonce The nonce used in the signature.
 * @property signatureTimestamp The timestamp used in the signature.
 */
@Serializable
data class CompliancePayerData(
    val utxos: List<String>,
    val nodePubKey: String?,
    val senderKycStatus: KycStatus,
    val travelRuleInfo: String?,
    val utxoCallback: String,
    val signature: String,
    val signatureNonce: String,
    val signatureTimestamp: Long,
) {
    fun signedWith(signature: String) = copy(signature = signature)
}
