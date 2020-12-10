package org.AimEx.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import org.ligi.trulesk.TruleskActivityRule
import org.AimEx.R
import org.AimEx.info.AimExInfoActivity

class TheAimExInfoActivity {

    @get:Rule
    var rule = TruleskActivityRule(AimExInfoActivity::class.java)

    @Test
    fun infoShows() {
        onView(withId(R.id.intro_text)).check(matches(isDisplayed()))

        rule.screenShot("info")
    }

}
