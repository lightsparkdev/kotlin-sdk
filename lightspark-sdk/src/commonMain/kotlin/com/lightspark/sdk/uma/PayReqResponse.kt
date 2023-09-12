package com.lightspark.sdk.uma

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The response sent by the receiver to the sender to provide an invoice.
 *
 * @property encodedInvoice The BOLT11 invoice that the sender will pay.
 * @property routes Usually just an empty list from legacy LNURL, which was replaced by route hints in the BOLT11
 *     invoice.
 * @property compliance The compliance data from the receiver, including utxo info.
 * @property paymentInfo The payment info from the receiver, including currency and an updated conversion rate.
 */
@Serializable
data class PayReqResponse(
    @SerialName("pr")
    val encodedInvoice: String,
    val compliance: PayReqResponseCompliance,
    val paymentInfo: PayReqResponsePaymentInfo,
    val routes: List<Route> = emptyList(),
)

@Serializable
data class Route(
    val pubkey: String,
    val path: List<RouteHop>,
)

@Serializable
data class RouteHop(
    val pubkey: String,
    val channel: String,
    val fee: Long,
    val msatoshi: Long,
)

/**
 * The compliance data from the receiver, including utxo info.
 *
 * @property utxos A list of UTXOs of channels over which the receiver will likely receive the payment.
 * @property nodePubKey If known, the public key of the receiver's node. If supported by the sending VASP's compliance
 *     provider, this will be used to pre-screen the receiver's UTXOs for compliance purposes.
 * @property utxoCallback The URL that the sender VASP will call to send UTXOs of the channel that the sender used to
 *     send the payment once it completes.
 */
@Serializable
data class PayReqResponseCompliance(
    val utxos: List<String>,
    val nodePubKey: String?,
    val utxoCallback: String,
)

/**
 * The payment info from the receiver.
 *
 * @property currencyCode The currency code that the receiver will receive for this payment.
 * @property multiplier The conversion rate. It is the number of millisatoshis that the receiver will receive for 1
 *     unit of the specified currency (eg: cents in USD)
 */
@Serializable
data class PayReqResponsePaymentInfo(
    val currencyCode: String,
    val multiplier: Long,
)
