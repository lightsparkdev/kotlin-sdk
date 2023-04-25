//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.util](../index.md)/[EnumSerializer](index.md)

# EnumSerializer

[common]\
open class [EnumSerializer](index.md)&lt;[T](index.md) : Enum&lt;[T](index.md)&gt;&gt;(enumClass: KClass&lt;[T](index.md)&gt;, fromString: (String) -&gt; [T](index.md)) : KSerializer&lt;[T](index.md)&gt;

## Constructors

| | |
|---|---|
| [EnumSerializer](-enum-serializer.md) | [common]<br>fun &lt;[T](index.md) : Enum&lt;[T](index.md)&gt;&gt; [EnumSerializer](-enum-serializer.md)(enumClass: KClass&lt;[T](index.md)&gt;, fromString: (String) -&gt; [T](index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | [common]<br>open override fun [deserialize](deserialize.md)(decoder: Decoder): [T](index.md) |
| [serialize](serialize.md) | [common]<br>open override fun [serialize](serialize.md)(encoder: Encoder, value: [T](index.md)) |

## Properties

| Name | Summary |
|---|---|
| [descriptor](descriptor.md) | [common]<br>open override val [descriptor](descriptor.md): SerialDescriptor |
