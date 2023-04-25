//[lightspark-core](../../index.md)/[com.lightspark.sdk.core](index.md)/[wrapWithLceFlow](wrap-with-lce-flow.md)

# wrapWithLceFlow

[common]\
fun &lt;[T](wrap-with-lce-flow.md)&gt; [wrapWithLceFlow](wrap-with-lce-flow.md)(query: suspend () -&gt; [T](wrap-with-lce-flow.md)?): Flow&lt;[Lce](-lce/index.md)&lt;[T](wrap-with-lce-flow.md)&gt;&gt;

A convenience function which wraps a query in a Flow that emits [Lce](-lce/index.md) states for loading, success, and error conditions.

For example:

```kotlin
val lceDashboard = wrapFlowableResult { lightsparkClient.getFullAccountDashboard() }
```

#### Return

A Flow which emits [Lce](-lce/index.md) states for loading, success, and error conditions for the given query.
