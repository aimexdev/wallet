package org.AimEx.base_activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.AimEx.data.config.Settings

@SuppressLint("Registered")
open class AimExActivity : AppCompatActivity() {

    protected val settings: Settings by inject()

    public override fun onCreate(savedInstanceState: Bundle?) {
        if (settings.isScreenshotsDisabled()) {
            window.setFlags(FLAG_SECURE, FLAG_SECURE)
        }
        super.onCreate(savedInstanceState)
    }

}