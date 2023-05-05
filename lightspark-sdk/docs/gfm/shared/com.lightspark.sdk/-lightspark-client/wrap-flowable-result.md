//[shared](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[wrapFlowableResult](wrap-flowable-result.md)

# wrapFlowableResult

[common]\
fun &lt;[T](wrap-flowable-result.md)&gt; [wrapFlowableResult](wrap-flowable-result.md)(query: suspend () -&gt; [T](wrap-flowable-result.md)?): Flow&lt;[Lce](../-lce/index.md)&lt;[T](wrap-flowable-result.md)&gt;&gt;

A convenience function which wraps a query in a Flow that emits [Lce](../-lce/index.md) states for loading, success, and error conditions.

For example:

```kotlin
val lceDashboard = wrapFlowableResult { lightsparkClient.getFullAccountDashboard() }
```

#### Return

A Flow which emits [Lce](../-lce/index.md) states for loading, success, and error conditions for the given query.
