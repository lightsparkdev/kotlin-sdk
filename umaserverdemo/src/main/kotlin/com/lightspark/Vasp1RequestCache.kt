package com.lightspark

import com.lightspark.sdk.model.InvoiceData
import me.uma.protocol.LnurlpResponse
import java.util.UUID

/**
 * A simple in-memory cache for data that needs to be remembered between calls to VASP1. In practice, this would be
 * stored in a database or other persistent storage.
 */
class Vasp1RequestCache {
    /**
     *  This is a map of the UMA request UUID to the LnurlpResponse from that initial Lnurlp request.
     * 	This is used to cache the LnurlpResponse so that we can use it to generate the UMA payreq without the client
     * 	having to make another Lnurlp request or remember lots of details.
     * 	NOTE: In production, this should be stored in a database or other persistent storage.
     */
    private val lnurlpRequestCache: MutableMap<String, Vasp1InitialRequestData> = mutableMapOf()

    /**
     * This is a map of the UMA request UUID to the payreq data that we generated for that request.
     * This is used to cache the payreq data so that we can pay the invoice when the user confirms
     * NOTE: In production, this should be stored in a database or other persistent storage.
     */
    private val payReqCache: MutableMap<String, Vasp1PayReqData> = mutableMapOf()

    fun getLnurlpResponseData(uuid: String): Vasp1InitialRequestData? {
        return lnurlpRequestCache[uuid]
    }

    fun getPayReqData(uuid: String): Vasp1PayReqData? {
        return payReqCache[uuid]
    }

    fun saveLnurlpResponseData(lnurlpResponse: LnurlpResponse, receiverId: String, vasp2Domain: String): String {
        val uuid = UUID.randomUUID().toString()
        lnurlpRequestCache[uuid] = Vasp1InitialRequestData(lnurlpResponse, receiverId, vasp2Domain)
        return uuid
    }

    fun savePayReqData(encodedInvoice: String, utxoCallback: String, invoiceData: InvoiceData): String {
        val uuid = UUID.randomUUID().toString()
        payReqCache[uuid] = Vasp1PayReqData(encodedInvoice, utxoCallback, invoiceData)
        return uuid
    }

    fun removeLnurlpResponseData(uuid: String) {
        lnurlpRequestCache.remove(uuid)
    }

    fun removePayReqData(uuid: String) {
        payReqCache.remove(uuid)
    }
}

data class Vasp1InitialRequestData(
    val lnurlpResponse: LnurlpResponse,
    val receiverId: String,
    val vasp2Domain: String,
)

data class Vasp1PayReqData(
    val encodedInvoice: String,
    val utxoCallback: String,
    val invoiceData: InvoiceData,
)

