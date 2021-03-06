package org.AimEx.accounts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_account_type_select.*
import org.AimEx.R
import org.AimEx.base_activities.BaseSubActivity
import org.AimEx.data.*
import org.AimEx.data.addresses.AccountKeySpec

class ImportAsActivity : BaseSubActivity() {

    private val inSpec by lazy {
        intent.getParcelableExtra<AccountKeySpec>(EXTRA_KEY_ACCOUNTSPEC).copy(source = "import")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account_type_select)

        supportActionBar?.subtitle = getString(R.string.import_as_subtitle)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = AccountTypeAdapter(ACCOUNT_TYPE_LIST.filter { it.wrapsKey }, inSpec)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE_ENTER_PASSWORD -> {
                    val pwdExtra = data.getStringExtra(EXTRA_KEY_PWD)
                    val spec = inSpec.copy(type = ACCOUNT_TYPE_PASSWORD_PROTECTED, pwd = pwdExtra)
                    setResult(resultCode, data.putExtra(EXTRA_KEY_ACCOUNTSPEC, spec))
                }
                REQUEST_CODE_ENTER_PIN -> {
                    val pinExtra = data.getStringExtra(EXTRA_KEY_PIN)
                    val spec =  inSpec.copy(type = ACCOUNT_TYPE_PIN_PROTECTED, pwd = pinExtra)
                    setResult(resultCode, data.putExtra(EXTRA_KEY_ACCOUNTSPEC, spec))
                }
                else -> {
                    setResult(resultCode, data)
                }
            }
            finish()


        }
    }
}
