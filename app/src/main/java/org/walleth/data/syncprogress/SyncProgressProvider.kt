package org.AimEx.data.syncprogress

import androidx.lifecycle.MutableLiveData

class SyncProgressProvider : MutableLiveData<AimExSyncProgress>() {

    init {
        value = AimExSyncProgress(false, 0L, 0L)
    }
}