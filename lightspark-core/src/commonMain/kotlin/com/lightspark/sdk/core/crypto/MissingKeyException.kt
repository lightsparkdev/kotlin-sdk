package com.lightspark.sdk.core.crypto

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException

class MissingKeyException(nodeId: String) : LightsparkException(
    "Node key not loaded for node $nodeId",
    LightsparkErrorCode.MISSING_NODE_KEY,
)
