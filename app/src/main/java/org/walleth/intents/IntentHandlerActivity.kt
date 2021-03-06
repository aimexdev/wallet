package org.AimEx.intents

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import org.kethereum.erc1328.isERC1328
import org.kethereum.erc681.ERC681
import org.kethereum.erc681.isERC681
import org.kethereum.erc681.toERC681
import org.kethereum.erc831.isERC831
import org.kethereum.erc831.toERC831
import org.kethereum.keystore.api.KeyStore
import org.kethereum.model.Address
import org.kethereum.model.EthereumURI
import org.koin.android.ext.android.inject
import org.ligi.kaxtui.alert
import org.AimEx.R
import org.AimEx.accounts.AccountPickActivity
import org.AimEx.accounts.startCreateAccountActivity
import org.AimEx.base_activities.AimExActivity
import org.AimEx.data.REQUEST_CODE_CREATE_TX
import org.AimEx.data.REQUEST_CODE_SELECT_TO_ADDRESS
import org.AimEx.data.REQUEST_CODE_SIGN_TX
import org.AimEx.data.addresses.CurrentAddressProvider
import org.AimEx.data.tokens.isTokenTransfer
import org.AimEx.sign.SignTextActivity
import org.AimEx.transactions.CreateTransactionActivity
import org.AimEx.walletconnect.WalletConnectConnectionActivity
import java.math.BigInteger.ZERO

fun Context.getEthereumViewIntent(ethereumString: String) = Intent(this, IntentHandlerActivity::class.java).apply {
    data = Uri.parse(ethereumString)
}

class IntentHandlerActivity : AimExActivity() {

    private var textToSign: String? = null

    private val currentAddressProvider: CurrentAddressProvider by inject()
    private val keyStore: KeyStore by inject()
    private var oldFilterAddressesKeyOnly = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentDataString = intent?.data.toString()
        val ethereumURI = EthereumURI(intentDataString).toERC831()

        if (ethereumURI.isERC1328()) {
            val wcIntent = Intent(this, WalletConnectConnectionActivity::class.java)
            wcIntent.data = intent.data
            startActivity(wcIntent)
            finish()
        } else if (ethereumURI.isERC831()) {
            if (ethereumURI.isERC681()) {
                val erC681 = ethereumURI.toERC681()
                if (erC681.valid) {
                    process681(erC681)
                } else {
                    alert("This URL is illegal ERC-681. Please contact the provider to change this!") {
                        finish()
                    }
                }
            }

            if (ethereumURI.prefix == "esm") {
                val split = ethereumURI.payload?.split("/")
                if (split?.size != 2) {
                    alert("Invalid request. " + intent.data.toString() + " If you think this should be valid - please drop ligi a mail (AimEx@AimEx.org).")
                    return
                }

                textToSign = split.last()
                if (split.first().isEmpty()) {
                    oldFilterAddressesKeyOnly = settings.filterAddressesKeyOnly
                    settings.filterAddressesKeyOnly = true
                    val intent = Intent(this, AccountPickActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_SELECT_TO_ADDRESS)
                } else {
                    val wantedAddress = Address(split.first())

                    if (keyStore.hasKeyForForAddress(wantedAddress)) {
                        currentAddressProvider.setCurrent(wantedAddress)
                        startEthereumSignedMessage()
                    } else {
                        alert("Don't have the key for the requested address $wantedAddress")
                    }
                }
            }

        } else {
            alert(getString(R.string.create_tx_error_invalid_url_msg, intentDataString), getString(R.string.create_tx_error_invalid_url_title)) {
                finish()
            }
        }
    }


    private fun process681(erc681: ERC681) {
        if (erc681.function != null || erc681.address == null || erc681.isTokenTransfer() || erc681.value != null && erc681.value != ZERO) {
            startActivityForResult(Intent(this, CreateTransactionActivity::class.java).apply {
                data = intent.data
            }, REQUEST_CODE_CREATE_TX)
        } else {
            AlertDialog.Builder(this)
                    .setTitle(R.string.select_action_messagebox_title)
                    .setItems(R.array.scan_hex_choices) { _, which ->
                        when (which) {
                            0 -> {
                                startCreateAccountActivity(erc681.address!!)
                                finish()
                            }
                            1 -> {
                                val intent = Intent(this, CreateTransactionActivity::class.java).apply {
                                    data = intent.data
                                }
                                startActivityForResult(intent, REQUEST_CODE_CREATE_TX)
                            }
                            2 -> alert("TODO", "add token definition") {
                                finish()
                            }

                        }
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->
                        finish()
                    }
                    .setOnCancelListener {
                        finish()
                    }
                    .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SIGN_TX, REQUEST_CODE_CREATE_TX -> {
                setResult(resultCode, data)
                finish()
            }

            REQUEST_CODE_SELECT_TO_ADDRESS -> {

                if (resultCode == Activity.RESULT_OK) {
                    if (data?.hasExtra("HEX") == true) {
                        currentAddressProvider.setCurrent(Address(data.getStringExtra("HEX")))
                    }

                    settings.filterAddressesKeyOnly = oldFilterAddressesKeyOnly
                    startEthereumSignedMessage()
                } else {
                    finish()
                }
            }
        }
    }

    private fun startEthereumSignedMessage() {
        val intent = Intent(this, SignTextActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, textToSign)
        startActivityForResult(intent, REQUEST_CODE_SIGN_TX)
    }
}
