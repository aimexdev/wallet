package org.AimEx.walletconnect

import android.content.Intent
import android.os.Bundle
import org.ligi.kaxtui.alert
import org.AimEx.base_activities.BaseSubActivity


class WalletConnectErrorActivity : BaseSubActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alert(intent.getStringExtra(Intent.EXTRA_TEXT)) {
            finish()
        }
    }
}
