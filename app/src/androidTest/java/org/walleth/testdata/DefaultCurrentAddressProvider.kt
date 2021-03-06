package org.AimEx.testdata

import org.kethereum.crypto.createEthereumKeyPair
import org.kethereum.crypto.toAddress
import org.AimEx.data.DEFAULT_PASSWORD
import org.AimEx.data.config.Settings
import org.AimEx.data.addresses.CurrentAddressProvider

val DEFAULT_TEST_KEY = createEthereumKeyPair()
val DEFAULT_TEST_KEY2 = createEthereumKeyPair()
val DEFAULT_TEST_KEY3 = createEthereumKeyPair()

val DEFAULT_TEST_ADDRESS = DEFAULT_TEST_KEY.toAddress()
val DEFAULT_TEST_ADDRESS2 = DEFAULT_TEST_KEY2.toAddress()
val DEFAULT_TEST_ADDRESS3 = DEFAULT_TEST_KEY3.toAddress()

class DefaultCurrentAddressProvider(settings: Settings, keyStore: TestKeyStore) : CurrentAddressProvider(settings) {

    init {
        keyStore.addKey(DEFAULT_TEST_KEY, DEFAULT_PASSWORD, true)
        keyStore.addKey(DEFAULT_TEST_KEY2, DEFAULT_PASSWORD, true)
        keyStore.addKey(DEFAULT_TEST_KEY3, DEFAULT_PASSWORD, true)
        setCurrent(DEFAULT_TEST_ADDRESS)

    }

}