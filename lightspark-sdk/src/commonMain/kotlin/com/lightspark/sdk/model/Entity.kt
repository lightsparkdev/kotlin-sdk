// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName

/**
 * This interface is used by all the entities in the Lightspark system. It defines a few core fields that are available everywhere. Any object that implements this interface can be queried using the `entity` query and its ID.
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when the entity was first created.
 * @property updatedAt The date and time when the entity was last updated.
 */
interface Entity {
    @SerialName("entity_id")
    val id: String

    @SerialName("entity_created_at")
    val createdAt: Instant

    @SerialName("entity_updated_at")
    val updatedAt: Instant

    companion object {
    }
}
