package org.AimEx.infrastructure

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.room.Room
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.kethereum.DEFAULT_GAS_PRICE
import org.kethereum.keystore.api.KeyStore
import org.kethereum.methodsignatures.CachedOnlineMethodSignatureRepository
import org.kethereum.methodsignatures.model.TextMethodSignature
import org.kethereum.rpc.EthereumRPC
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.walletconnect.impls.WCSessionStore
import org.AimEx.App
import org.AimEx.chains.ChainInfoProvider
import org.AimEx.data.AppDatabase
import org.AimEx.data.addresses.CurrentAddressProvider
import org.AimEx.data.config.Settings
import org.AimEx.data.exchangerate.ExchangeRateProvider
import org.AimEx.data.rpc.RPCProvider
import org.AimEx.data.syncprogress.SyncProgressProvider
import org.AimEx.data.syncprogress.AimExSyncProgress
import org.AimEx.data.tokens.CurrentTokenProvider
import org.AimEx.overview.TransactionListViewModel
import org.AimEx.testdata.DefaultCurrentAddressProvider
import org.AimEx.testdata.FixedValueExchangeProvider
import org.AimEx.testdata.TestKeyStore
import org.AimEx.util.jsonadapter.BigIntegerJSONAdapter
import org.AimEx.walletconnect.WalletConnectViewModel
import java.math.BigInteger.ZERO

private fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T

class TestApp : App() {

    override fun createKoin() = module {
        single { fixedValueExchangeProvider as ExchangeRateProvider }
        single {
            SyncProgressProvider().apply {
                value = AimExSyncProgress(true, 42000, 42042)
            }
        }
        single { keyStore as KeyStore }
        single { mySettings }
        single { currentAddressProvider as CurrentAddressProvider }
        single { chainInfoProvider }
        single { currentTokenProvider }
        single { testDatabase }
        single { testFourByteDirectory }
        single {
            mock(RPCProvider::class.java).apply {
                runBlocking {
                    `when`(get()).thenReturn(RPCMock)
                }
            }
        }

        single {
            mock(WCSessionStore::class.java)
        }

        single {
            mock(OkHttpClient::class.java)
        }

        viewModel { WalletConnectViewModel(this@TestApp, get(), get(), get()) }
        viewModel { TransactionListViewModel(this@TestApp, get(), get(), get()) }
    }

    override fun executeCodeWeWillIgnoreInTests() = Unit
    override fun onCreate() {
        companionContext = this
        resetDB()
        super.onCreate()
    }

    companion object {
        val RPCMock: EthereumRPC = mock(EthereumRPC::class.java).apply {
            `when`(estimateGas(any())).thenReturn(ZERO)
        }
        val fixedValueExchangeProvider = FixedValueExchangeProvider()
        val keyStore = TestKeyStore()
        val mySettings: Settings = mock(Settings::class.java).apply {
            `when`(currentFiat).thenReturn("EUR")
            `when`(getNightMode()).thenReturn(MODE_NIGHT_YES)
            `when`(onboardingDone).thenReturn(true)
            `when`(chain).thenReturn(4L)
            `when`(isLightClientWanted()).thenReturn(false)
            `when`(addressInitVersion).thenReturn(0)
            `when`(tokensInitVersion).thenReturn(0)
            `when`(getGasPriceFor(any())).thenReturn(DEFAULT_GAS_PRICE)
        }
        val currentAddressProvider = DefaultCurrentAddressProvider(mySettings, keyStore)
        val chainInfoProvider by lazy {
            ChainInfoProvider(mySettings, testDatabase, Moshi.Builder().add(BigIntegerJSONAdapter()).build(), companionContext!!.assets)
        }
        val currentTokenProvider by lazy {
            CurrentTokenProvider(chainInfoProvider)
        }

        const val contractFunctionTextSignature1 = "aFunctionCall1(address)"
        const val contractFunctionTextSignature2 = "aFunctionCall2(address)"
        val testFourByteDirectory: CachedOnlineMethodSignatureRepository = mock(CachedOnlineMethodSignatureRepository::class.java).apply {
            `when`(getSignaturesFor(any())).then {
                listOf(
                        TextMethodSignature(contractFunctionTextSignature1),
                        TextMethodSignature(contractFunctionTextSignature2)
                )
            }
        }

        val testDatabase by lazy {
            Room.inMemoryDatabaseBuilder(companionContext!!, AppDatabase::class.java).build()
        }
        var companionContext: Context? = null
        fun resetDB() {
            GlobalScope.launch(Dispatchers.Default) {
                testDatabase.clearAllTables()
            }
        }

    }
}
