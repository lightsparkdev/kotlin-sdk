package com.lightspark.sdk.uma

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
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
 */
@Serializable
data class LnrulpResponse(
    val callback: String,
    val minSendable: Long,
    val maxSendable: Long,
    val metadata: String,
    val currencies: List<Currency>,
    @SerialName("payerData")
    val requiredPayerData: PayerDataOptions,
    val compliance: LnurlComplianceResponse,
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
    override val descriptor = PrimitiveSerialDescriptor("PayerDataOptions", PrimitiveKind.STRING)

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
        val compositeInput = decoder.beginStructure(descriptor)
        var nameRequired = false
        var emailRequired = false
        var complianceRequired = false
        loop@ while (true) {
            when (val index = compositeInput.decodeElementIndex(descriptor)) {
                CompositeDecoder.Companion.DECODE_DONE -> break@loop
                0 -> {
                    val jsonInput = compositeInput.decodeStringElement(descriptor, index)
                    val json = Json.parseToJsonElement(jsonInput)
                    val name = json.jsonObject["name"]?.jsonObject
                    val email = json.jsonObject["email"]?.jsonObject
                    val compliance = json.jsonObject["compliance"]?.jsonObject
                    nameRequired = name?.get("mandatory")?.jsonPrimitive?.boolean ?: false
                    emailRequired = email?.get("mandatory")?.jsonPrimitive?.boolean ?: false
                    complianceRequired = compliance?.get("mandatory")?.jsonPrimitive?.boolean ?: false
                }

                else -> throw IllegalArgumentException("Invalid index $index")
            }
        }
        compositeInput.endStructure(descriptor)
        return PayerDataOptions(nameRequired, emailRequired, complianceRequired)
    }
}
