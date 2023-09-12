package com.lightspark.sdk.uma

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Response from VASP2 to the [LnurlpRequest].
 *
 * @property callback The URL which should be used by VASP1 for the [PayRequest].
 * @property minSendable The minimum amount that the receiver can receive in millisatoshis.
 * @property maxSendable The maximum amount that the receiver can receive in millisatoshis.
 * @property metadata Encoded metadata that can be used to verify the invoice later. See the
 *     [LUD-06 Spec](https://github.com/lnurl/luds/blob/luds/06.md).
 * @property currencies The list of currencies that the receiver accepts.
 * @property requiredPayerData The data that the sender must send to the receiver to identify themselves.
 * @property compliance The compliance data from the receiver, including TR status, kyc info, etc.
 * @property umaVersion The version of the UMA protocol that VASP2 has chosen for this transaction based on its own
 *     support and VASP1's specified preference in the LnurlpRequest. For the version negotiation flow, see
 * 	   https://static.swimlanes.io/87f5d188e080cb8e0494e46f80f2ae74.png
 */
@Serializable
data class LnurlpResponse(
    val callback: String,
    val minSendable: Long,
    val maxSendable: Long,
    val metadata: String,
    val currencies: List<Currency>,
    @SerialName("payerData")
    val requiredPayerData: PayerDataOptions,
    val compliance: LnurlComplianceResponse,
    val umaVersion: String,
    val tag: String = "payRequest",
)

@Serializable(with = PayerDataOptionsSerializer::class)
data class PayerDataOptions(
    val nameRequired: Boolean,
    val emailRequired: Boolean,
    val complianceRequired: Boolean,
)

// Custom serializer for PayerDataOptions
class PayerDataOptionsSerializer : KSerializer<PayerDataOptions> {
    override val descriptor = PrimitiveSerialDescriptor("payerData", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PayerDataOptions) {
        val jsonOutput = """{
            "identifier": true,
            "name": { "mandatory": ${value.nameRequired} },
            "email": { "mandatory": ${value.emailRequired} },
            "compliance": { "mandatory": ${value.complianceRequired} }
        }""".trimIndent()
        encoder.encodeString(jsonOutput)
    }

    override fun deserialize(decoder: Decoder): PayerDataOptions {
        val jsonInput = decoder.decodeString()
        val json = Json.parseToJsonElement(jsonInput)
        val name = json.jsonObject["name"]?.jsonObject
        val email = json.jsonObject["email"]?.jsonObject
        val compliance = json.jsonObject["compliance"]?.jsonObject
        val nameRequired = name?.get("mandatory")?.jsonPrimitive?.boolean ?: false
        val emailRequired = email?.get("mandatory")?.jsonPrimitive?.boolean ?: false
        val complianceRequired = compliance?.get("mandatory")?.jsonPrimitive?.boolean ?: false
        return PayerDataOptions(nameRequired, emailRequired, complianceRequired)
    }
}
