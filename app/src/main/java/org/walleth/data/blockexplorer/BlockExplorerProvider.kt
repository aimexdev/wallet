package org.AimEx.data.blockexplorer

import android.content.Context
import org.kethereum.model.BlockExplorer
import org.ligi.kaxtui.alert
import org.AimEx.chains.ChainInfoProvider
import org.AimEx.kethereum.blockscout.getBlockScoutBlockExplorer
import org.AimEx.kethereum.etherscan.getEtherScanBlockExplorer

class BlockExplorerProvider(var network: ChainInfoProvider) {

    fun get() = getBlockScoutBlockExplorer(network.getCurrentChainId())?: getEtherScanBlockExplorer(network.getCurrentChainId())

    fun getOrAlert(context: Context): BlockExplorer? {
        val result = get()
        if (result == null) {
            context.alert("No blockExplorer found for the current Network")
        }
        return result
    }
}

