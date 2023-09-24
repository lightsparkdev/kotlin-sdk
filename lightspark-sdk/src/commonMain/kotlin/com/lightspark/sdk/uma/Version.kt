package com.lightspark.sdk.uma

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

const val MAJOR_VERSION = 0
const val MINOR_VERSION = 1
const val UMA_VERSION_STRING = "$MAJOR_VERSION.$MINOR_VERSION"

/**
 * The supported major versions of this UMA SDK.
 *
 * NOTE: In the future, we may want to support multiple major versions in the same SDK, but for now, this keeps
 * things simple.
 */
fun supportedMajorVersions() = setOf(MAJOR_VERSION)

data class Version(val major: Int, val minor: Int) : Comparable<Version> {
    override fun compareTo(other: Version): Int {
        return when {
            major > other.major -> 1
            major < other.major -> -1
            minor > other.minor -> 1
            minor < other.minor -> -1
            else -> 0
        }
    }

    override fun toString(): String {
        return "$major.$minor"
    }

    companion object {
        fun parse(versionString: String): Version {
            val parts = versionString.split(".")
            if (parts.size != 2) {
                throw IllegalArgumentException("Invalid version string: $versionString")
            }
            return Version(parts[0].toInt(), parts[1].toInt())
        }
    }
}

class UnsupportedVersionException(
    val unsupportedVersion: String,
    val supportedMajorVersions: Set<Int> = supportedMajorVersions(),
) : Exception("Unsupported version: $unsupportedVersion. Supported major versions: $supportedMajorVersions") {
    fun toLnurlpResponseJson(): String {
        return buildJsonObject {
            put("reason", "Unsupported version: ${unsupportedVersion}.")
            put("supportedMajorVersions", Json.encodeToJsonElement(supportedMajorVersions))
            put("unsupportedVersion", unsupportedVersion)
        }.toString()
    }
}

fun isVersionSupported(versionString: String): Boolean {
    val version = try {
        Version.parse(versionString)
    } catch (e: IllegalArgumentException) {
        return false
    } catch (e: NumberFormatException) {
        return false
    }
    return supportedMajorVersions().contains(version.major)
}

fun selectHighestSupportedVersion(otherVaspSupportedMajorVersions: List<Int>): String? {
    val highestSupportedMajorVersion = otherVaspSupportedMajorVersions.filter {
        supportedMajorVersions().contains(it)
    }.maxOrNull() ?: return null
    return getHighestSupportedVersionForMajorVersion(highestSupportedMajorVersion)
}

private fun getHighestSupportedVersionForMajorVersion(majorVersion: Int): String? {
    // Note that this also only supports a single major version for now. If we support more than one major version in
    // the future, we'll need to change this.
    return if (majorVersion == MAJOR_VERSION) UMA_VERSION_STRING else null
}
