package org.AimEx.preferences

import android.os.Bundle
import org.AimEx.R
import org.AimEx.base_activities.BaseSubActivity
import org.AimEx.toolbar.DefaultToolbarChangeDetector
import org.AimEx.toolbar.ToolbarColorChangeDetector

class PreferenceActivity : BaseSubActivity() ,  ToolbarColorChangeDetector by DefaultToolbarChangeDetector() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_prefs)

        supportActionBar?.subtitle = getString(R.string.preferences_activity_subtitle)
    }

    override fun onResume() {
        super.onResume()

        if (didToolbarColorChange()) {
            recreate()
        }
    }
}