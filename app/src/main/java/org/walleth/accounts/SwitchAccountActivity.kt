package org.AimEx.accounts

import android.os.Bundle
import org.AimEx.R
import org.AimEx.data.addresses.AddressBookEntry

class SwitchAccountActivity : BaseAddressBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.subtitle = getString(R.string.nav_drawer_accounts)
    }

    override fun onAddressClick(addressEntry: AddressBookEntry) {
        currentAddressProvider.setCurrent(addressEntry.address)
        finish()
    }
}
