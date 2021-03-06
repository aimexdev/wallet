package org.AimEx.util

import org.AimEx.data.chaininfo.ChainInfo
import org.AimEx.data.rpc.KEY_IN3_RPC


fun ChainInfo.getRPCEndpoint() = if (rpc.isNotEmpty()) {
    rpc.random().replace("\${INFURA_API_KEY}", "b032785efb6947ceb18b9e0177053a17")
} else { null }

fun ChainInfo.hasTincubethSupport() = rpc.contains(KEY_IN3_RPC)