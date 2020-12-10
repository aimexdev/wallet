package org.AimEx.toolbar

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.AimEx.data.config.Settings

interface ToolbarColorChangeDetector {
    val AimExSettings: Settings
    var lastToolbarColor: Long
    fun calcToolbarColorCombination() = AimExSettings.toolbarBackgroundColor.toLong() + AimExSettings.toolbarForegroundColor

    fun didToolbarColorChange() = (lastToolbarColor != calcToolbarColorCombination()).also {
        lastToolbarColor = calcToolbarColorCombination()
    }

}

class DefaultToolbarChangeDetector : ToolbarColorChangeDetector, KoinComponent {
    override val AimExSettings: Settings by inject()
    override var lastToolbarColor: Long = calcToolbarColorCombination()
}