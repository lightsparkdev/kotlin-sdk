//[lightspark-core](../../index.md)/[com.lightspark.sdk.core.util](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AndroidPlatform](-android-platform/index.md) | [android]<br>class [AndroidPlatform](-android-platform/index.md) : Platform |
| [EnumSerializer](-enum-serializer/index.md) | [common]<br>open class [EnumSerializer](-enum-serializer/index.md)&lt;[T](-enum-serializer/index.md) : Enum&lt;[T](-enum-serializer/index.md)&gt;&gt;(enumClass: KClass&lt;[T](-enum-serializer/index.md)&gt;, fromString: (String) -&gt; [T](-enum-serializer/index.md)) : KSerializer&lt;[T](-enum-serializer/index.md)&gt; |
| [IosPlatform](-ios-platform/index.md) | [ios]<br>class [IosPlatform](-ios-platform/index.md) : Platform |
| [JvmPlatform](-jvm-platform/index.md) | [jvm]<br>open class [JvmPlatform](-jvm-platform/index.md) : Platform |
| [Platform](-platform/index.md) | [common]<br>interface [Platform](-platform/index.md) |

## Functions

| Name | Summary |
|---|---|
| [format](format.md) | [common, ios]<br>[common]<br>expect fun LocalDateTime.[format](format.md)(format: String): String<br>[ios]<br>actual fun LocalDateTime.[format](format.md)(format: String): String |
| [getPlatform](get-platform.md) | [common, ios, jvm]<br>[common]<br>expect fun [getPlatform](get-platform.md)(): [Platform](-platform/index.md)<br>[ios, jvm]<br>actual fun [getPlatform](get-platform.md)(): Platform |
