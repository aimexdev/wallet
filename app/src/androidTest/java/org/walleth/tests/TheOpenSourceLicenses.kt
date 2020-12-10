package org.AimEx.tests

import org.junit.Rule
import org.junit.Test
import org.ligi.trulesk.TruleskIntentRule
import org.AimEx.info.OpenSourceLicenseInfoActivity


class TheOpenSourceLicenses {


        @get:Rule
        var rule = TruleskIntentRule(OpenSourceLicenseInfoActivity::class.java)

        @Test
        fun rejectsCriticallyLongAddress() {
            rule.screenShot("licenses")
        }

}
