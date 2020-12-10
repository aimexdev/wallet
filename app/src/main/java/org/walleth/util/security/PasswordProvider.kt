package org.AimEx.util.security

import androidx.fragment.app.FragmentActivity
import org.AimEx.R
import org.AimEx.credentials.getPassword
import org.AimEx.credentials.showAccountPinDialog
import org.AimEx.data.ACCOUNT_TYPE_BURNER
import org.AimEx.data.ACCOUNT_TYPE_PASSWORD_PROTECTED
import org.AimEx.data.ACCOUNT_TYPE_PIN_PROTECTED
import org.AimEx.data.DEFAULT_PASSWORD

fun getInvalidStringResForAccountType(type: String) = when (type){
    ACCOUNT_TYPE_PIN_PROTECTED -> R.string.invalid_pin
    ACCOUNT_TYPE_PASSWORD_PROTECTED -> R.string.invalid_password
    else -> throw IllegalArgumentException("getInvalidStringResForAccountType() must be called for ACCOUNT_TYPE_PIN_PROTECTED or ACCOUNT_TYPE_PASSWORD_PROTECTED")
}
fun FragmentActivity.getPasswordForAccountType(type: String, callback: (password: String?) -> Unit) = when (type) {
    ACCOUNT_TYPE_PIN_PROTECTED -> showAccountPinDialog { pwd ->
        callback(pwd)
    }
    ACCOUNT_TYPE_PASSWORD_PROTECTED -> getPassword { pwd ->
        callback(pwd)
    }
    ACCOUNT_TYPE_BURNER -> callback(DEFAULT_PASSWORD)
    else -> throw IllegalArgumentException("getPasswordForAccountType() must be called for ACCOUNT_TYPE_PIN_PROTECTED, ACCOUNT_TYPE_PASSWORD_PROTECTED or ACCOUNT_TYPE_BURNER but was $type")
}
