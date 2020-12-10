package org.AimEx.qr.scan

import android.net.Uri
import androidx.appcompat.app.AlertDialog
import org.kethereum.erc681.ERC681
import org.kethereum.erc681.generateURL
import org.kethereum.erc831.isEthereumURLString
import org.AimEx.R
import org.AimEx.accounts.KeyType
import org.AimEx.accounts.getCreateImportIntentFor
import org.AimEx.intents.getEthereumViewIntent
import org.AimEx.transactions.getOfflineTransactionIntent
import org.AimEx.util.isJSONKey
import org.AimEx.util.isParityUnsignedTransactionJSON
import org.AimEx.util.isSignedTransactionJSON
import org.AimEx.util.isUnsignedTransactionJSON
import org.AimEx.walletconnect.getWalletConnectIntent

class QRScanActivityAndProcessActivity : QRScanActivity() {

    override fun finishWithResult(value: String) {

        when {
            value.startsWith("wc:") -> {
                startActivity(getWalletConnectIntent(Uri.parse(value)))
            }

            value.isEthereumURLString() -> {
                startActivity(getEthereumViewIntent(value))
            }

            value.length == 64 -> {
                startActivity(getCreateImportIntentFor(value, KeyType.ECDSA))
            }

            value.isJSONKey() -> {
                startActivity(getCreateImportIntentFor(value, KeyType.JSON))
            }

            value.isUnsignedTransactionJSON() || value.isSignedTransactionJSON() || value.isParityUnsignedTransactionJSON() -> {
                startActivity(getOfflineTransactionIntent(value))
            }

            value.startsWith("0x") -> {
                startActivity(getEthereumViewIntent(ERC681(address = value).generateURL()))
            }

            else -> {
                AlertDialog.Builder(this)
                        .setMessage(R.string.scan_not_interpreted_error_message)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            this@QRScanActivityAndProcessActivity.finish()
                        }
                        .show()
            }
        }
    }

}