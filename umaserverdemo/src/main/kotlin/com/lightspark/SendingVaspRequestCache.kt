package com.lightspark

import com.lightspark.sdk.model.InvoiceData
import java.util.UUID
import me.uma.protocol.Invoice
import me.uma.protocol.LnurlpResponse

/**
 * A simple in-memory cache for data that needs to be remembered between calls to VASP1. In practice, this would be
 * stored in a database or other persistent storage.
 */
class SendingVaspRequestCache {
    /**
     *  This is a map of the UMA request UUID to the LnurlpResponse from that initial Lnurlp request.
     * 	This is used to cache the LnurlpResponse so that we can use it to generate the UMA payreq without the client
     * 	having to make another Lnurlp request or remember lots of details.
     * 	NOTE: In production, this should be stored in a database or other persistent storage.
     */
    private val lnurlpRequestCache: MutableMap<String, SendingVaspInitialRequestData> = mutableMapOf()

    /**
     * This is a map of the UMA request UUID to the payreq data that we generated for that request.
     * This is used to cache the payreq data so that we can pay the invoice when the user confirms
     * NOTE: In production, this should be stored in a database or other persistent storage.
     */
    private val payReqCache: MutableMap<String, SendingVaspPayReqData> = mutableMapOf()

    private val umaInvoiceCache: MutableMap<String, Invoice> = mutableMapOf()

    fun getLnurlpResponseData(uuid: String): SendingVaspInitialRequestData? {
        return lnurlpRequestCache[uuid]
    }

    fun getPayReqData(uuid: String): SendingVaspPayReqData? {
        return payReqCache[uuid]
    }

    fun getUmaInvoiceData(uuid: String): Invoice? {
        return umaInvoiceCache[uuid]
    }

    fun saveLnurlpResponseData(lnurlpResponse: LnurlpResponse, receiverId: String, vasp2Domain: String): String {
        val uuid = UUID.randomUUID().toString()
        lnurlpRequestCache[uuid] = SendingVaspInitialRequestData(lnurlpResponse, receiverId, vasp2Domain)
        return uuid
    }

    fun savePayReqData(encodedInvoice: String, utxoCallback: String, invoiceData: InvoiceData): String {
        val uuid = UUID.randomUUID().toString()
        payReqCache[uuid] = SendingVaspPayReqData(encodedInvoice, utxoCallback, invoiceData)
        return uuid
    }

    fun saveUmaInvoice(uuid: String, invoice: Invoice) {
        umaInvoiceCache[uuid] = invoice
    }

    fun removeLnurlpResponseData(uuid: String) {
        lnurlpRequestCache.remove(uuid)
    }

    fun removePayReqData(uuid: String) {
        payReqCache.remove(uuid)
    }

    fun removeUmaInvoiceData(uuid: String) {
        umaInvoiceCache.remove(uuid)
    }
}

data class SendingVaspInitialRequestData(
    val lnurlpResponse: LnurlpResponse,
    val receiverId: String,
    val receivingVaspDomain: String,
)

data class SendingVaspPayReqData(
    val encodedInvoice: String,
    val utxoCallback: String,
    val invoiceData: InvoiceData,
)
