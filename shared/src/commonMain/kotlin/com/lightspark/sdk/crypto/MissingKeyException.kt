package com.lightspark.sdk.crypto

import com.lightspark.sdk.LightsparkErrorCode
import com.lightspark.sdk.LightsparkException

class MissingKeyException(nodeId: String) : LightsparkException(
    "Node key not loaded for node $nodeId",
    LightsparkErrorCode.MISSING_NODE_KEY
)