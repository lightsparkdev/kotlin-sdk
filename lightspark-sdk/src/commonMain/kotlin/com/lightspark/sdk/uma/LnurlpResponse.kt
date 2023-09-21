package com.lightspark.sdk.uma

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json

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
) {
    fun toJson() = Json.encodeToString(this)
}

@Serializable(with = PayerDataOptionsSerializer::class)
data class PayerDataOptions(
    val nameRequired: Boolean,
    val emailRequired: Boolean,
    val complianceRequired: Boolean,
) {
    fun toJson() = Json.encodeToString(this)
}

@Serializable
data class PayerDataJsonField(
    val mandatory: Boolean,
)

// Custom serializer for PayerDataOptions
class PayerDataOptionsSerializer : KSerializer<PayerDataOptions> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("PayerDataOptions") {
            element<PayerDataJsonField>("identifier", isOptional = true)
            element<PayerDataJsonField>("name", isOptional = true)
            element<PayerDataJsonField>("email", isOptional = true)
            element<PayerDataJsonField>("compliance", isOptional = true)
        }

    override fun serialize(encoder: Encoder, value: PayerDataOptions) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, PayerDataJsonField.serializer(), PayerDataJsonField(true))
            encodeSerializableElement(descriptor, 1, PayerDataJsonField.serializer(), PayerDataJsonField(value.nameRequired))
            encodeSerializableElement(descriptor, 2, PayerDataJsonField.serializer(), PayerDataJsonField(value.emailRequired))
            encodeSerializableElement(descriptor, 3, PayerDataJsonField.serializer(), PayerDataJsonField(value.complianceRequired))
        }
    }

    override fun deserialize(decoder: Decoder): PayerDataOptions {
        return decoder.decodeStructure(descriptor) {
            var nameRequired = false
            var emailRequired = false
            var complianceRequired = false
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> {
                        val identifier = decodeSerializableElement(descriptor, 0, PayerDataJsonField.serializer())
                        if (!identifier.mandatory) {
                            throw IllegalArgumentException("PayerDataOptions.identifier must be mandatory")
                        }
                    }

                    1 -> {
                        val name = decodeSerializableElement(descriptor, 1, PayerDataJsonField.serializer())
                        nameRequired = name.mandatory
                    }

                    2 -> {
                        val email = decodeSerializableElement(descriptor, 2, PayerDataJsonField.serializer())
                        emailRequired = email.mandatory
                    }

                    3 -> {
                        val compliance = decodeSerializableElement(descriptor, 3, PayerDataJsonField.serializer())
                        complianceRequired = compliance.mandatory
                    }

                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            PayerDataOptions(nameRequired, emailRequired, complianceRequired)
        }
    }
}
