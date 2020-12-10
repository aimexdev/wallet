package org.AimEx.data.syncprogress

data class AimExSyncProgress(val isSyncing: Boolean = false, val currentBlock: Long=0, val highestBlock: Long=0)