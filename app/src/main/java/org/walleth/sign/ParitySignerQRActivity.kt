package org.AimEx.sign

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_parity_signer_qr.*
import org.koin.android.ext.android.inject
import org.AimEx.R
import org.AimEx.base_activities.BaseSubActivity
import org.AimEx.data.addresses.CurrentAddressProvider
import org.AimEx.util.setQRCode


class ParitySignerQRActivity : BaseSubActivity() {

    private val currentAddressProvider: CurrentAddressProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parity_signer_qr)

        if (intent.hasExtra("signatureHex")) {
            supportActionBar?.subtitle = "Parity signer QR code (Signed TX)"
            parity_address_qr_image.setQRCode(intent.getStringExtra("signatureHex"))
        } else {
            supportActionBar?.subtitle = "Address QR Code"

            parity_address_qr_image.setQRCode("ethereum:" + currentAddressProvider.getCurrent())
        }

    }
}
