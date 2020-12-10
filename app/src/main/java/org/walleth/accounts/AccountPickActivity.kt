package org.AimEx.accounts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.AimEx.R
import org.AimEx.data.EXTRA_KEY_ADDRESS
import org.AimEx.data.addresses.AddressBookEntry

class AccountPickActivity : BaseAddressBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.subtitle = getString(R.string.address_book_subtitle)
    }

    override fun onAddressClick(addressEntry: AddressBookEntry) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_KEY_ADDRESS, addressEntry.address.hex) })
        finish()
    }

}
