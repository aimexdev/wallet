package org.AimEx.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Rule
import org.junit.Test
import org.ligi.trulesk.TruleskActivityRule
import org.AimEx.R
import org.AimEx.securityinfo.SecurityInfoActivity

class TheSecurityAimExInfoActivity {

    @get:Rule
    var rule = TruleskActivityRule(SecurityInfoActivity::class.java)

    @Test
    fun infoShows() {
        onView(withText(R.string.security_info)).check(matches(isDisplayed()))

        rule.screenShot("security_info")
    }

}
