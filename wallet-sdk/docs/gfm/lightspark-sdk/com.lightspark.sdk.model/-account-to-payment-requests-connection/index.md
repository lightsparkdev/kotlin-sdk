//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[AccountToPaymentRequestsConnection](index.md)

# AccountToPaymentRequestsConnection

[common]\
@Serializable

data class [AccountToPaymentRequestsConnection](index.md)(val entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PaymentRequest](../-payment-request/index.md)&gt;, val pageInfo: [PageInfo](../-page-info/index.md), val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| entities | The payment requests for the current page of this connection. |
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |

## Constructors

| | |
|---|---|
| [AccountToPaymentRequestsConnection](-account-to-payment-requests-connection.md) | [common]<br>fun [AccountToPaymentRequestsConnection](-account-to-payment-requests-connection.md)(entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PaymentRequest](../-payment-request/index.md)&gt;, pageInfo: [PageInfo](../-page-info/index.md), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [entities](entities.md) | [common]<br>val [entities](entities.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PaymentRequest](../-payment-request/index.md)&gt; |
| [pageInfo](page-info.md) | [common]<br>val [pageInfo](page-info.md): [PageInfo](../-page-info/index.md) |
